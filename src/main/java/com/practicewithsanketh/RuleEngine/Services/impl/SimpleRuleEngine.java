package com.practicewithsanketh.RuleEngine.Services.impl;

import com.practicewithsanketh.RuleEngine.Models.Expense;
import com.practicewithsanketh.RuleEngine.Models.ExpenseType;
import com.practicewithsanketh.RuleEngine.Services.RuleEngine;
import com.practicewithsanketh.RuleEngine.Services.rules.ExpenseRule;
import com.practicewithsanketh.RuleEngine.Services.rules.TripRule;
import com.practicewithsanketh.RuleEngine.Services.rules.Violation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SimpleRuleEngine implements RuleEngine {

    @Override
    public List<Violation> evaluate(List<Expense> expenses,
                                    Map<ExpenseType, List<ExpenseRule>> expenseRulesRegistry,
                                    List<ExpenseRule> allExpenseRulesRegistry,
                                    List<TripRule> tripRulesRegistry) {

        List<Violation> violations = new ArrayList<>();

        if (expenses == null || expenses.isEmpty()) {
            return violations;
        }

        // 1. Evaluate Expense-level rules for each individual expense
        for (Expense expense : expenses) {
            // A. Type-specific rules (e.g. RESTAURANT, AIRFARE)
            if (expenseRulesRegistry != null && expense.getExpenseType() != null) {
                List<ExpenseRule> typeRules = expenseRulesRegistry.get(expense.getExpenseType());
                if (typeRules != null) {
                    for (ExpenseRule rule : typeRules) {
                        Optional<Violation> violation = rule.check(expense);
                        violation.ifPresent(violations::add);
                    }
                }
            }

            // B. Global expense rules (rules applicable to all expenses)
            if (allExpenseRulesRegistry != null) {
                for (ExpenseRule rule : allExpenseRulesRegistry) {
                    Optional<Violation> violation = rule.check(expense);
                    violation.ifPresent(violations::add);
                }
            }
        }

        // 2. Group expenses by tripId and evaluate Trip-level rules
        if (tripRulesRegistry != null && !tripRulesRegistry.isEmpty()) {
            Map<String, List<Expense>> expensesByTrip = new HashMap<>();
            for (Expense expense : expenses) {
                expensesByTrip
                        .computeIfAbsent(expense.getTripId(), k -> new ArrayList<>())
                        .add(expense);
            }

            for (List<Expense> tripExpenses : expensesByTrip.values()) {
                for (TripRule tripRule : tripRulesRegistry) {
                    Optional<Violation> violation = tripRule.check(tripExpenses);
                    violation.ifPresent(violations::add);
                }
            }
        }

        return violations;
    }
}

/*
The return type of `expenseRulesRegistry.get(...)` is indeed `List<ExpenseRule>`, not a single `ExpenseRule`.

This is directly determined by how the `Map` is declared in the method signature:

```java
Map<ExpenseType, List<ExpenseRule>> expenseRulesRegistry

```

In Java, a `Map<K, V>` represents a mapping between keys of type `K` and values of type `V`. The `.get(key)` method returns whatever type is assigned to `V`.

### Why `List<ExpenseRule>` is the Value Type

A single expense category (such as `RESTAURANT` or `AIRFARE`) often
needs **multiple validation checks** rather than just one:

* **For `RESTAURANT`:**
* Rule 1: Receipt must be attached if amount > $25.
* Rule 2: Tip cannot exceed 20%.
* Rule 3: Alcohol must be itemized.


* **For `AIRFARE`:**
* Rule 1: Must be booked in Economy class.
* Rule 2: Must be booked at least 14 days in advance.



Because one `ExpenseType` maps to several rules,
the map stores a collection (`List<ExpenseRule>`) for each key:

| Key (`ExpenseType`) | Value (`List<ExpenseRule>`) |
| --- | --- |
| `ExpenseType.RESTAURANT` | `[ReceiptRule, TipLimitRule, AlcoholRule]` |
| `ExpenseType.AIRFARE` | `[CabinClassRule, AdvanceBookingRule]` |

---

### How the Assignment Works Step-by-Step

```java
List<ExpenseRule> typeRules = expenseRulesRegistry.get(expense.getExpenseType());

```

1. **`expense.getExpenseType()`** returns an enum value, e.g., `ExpenseType.RESTAURANT`.
2. **`expenseRulesRegistry.get(...)`** looks up that key in the
`Map<ExpenseType, List<ExpenseRule>>`.
3. The map returns the value associated with that key, which is the entire `List<ExpenseRule>`.
4. Therefore, the receiving variable must match that value type: `List<ExpenseRule> typeRules`.

If the map had been declared as `Map<ExpenseType, ExpenseRule>`, each expense category could only have a single rule, and `.get(...)` would return just `ExpenseRule`. Using `List<ExpenseRule>` makes the design extensible so you can register as many rules per type as needed.
 */
/*
Analyzed SimpleRuleEngine.java:L1-126

Chalo, ab **`SimpleRuleEngine`** ke ek-ek purze (har line, har method, aur har return type) ko bilkul aam zindagi ke example ke saath khol kar dekhte hain.

Aapne jo AI ka text paste kiya, usme technical bhasha toh hai par **intuition (feel)** gayab thi. Aao isko step-by-step samajhte hain taaki aapke dimaag me picture crystal-clear ho jaye.

---

## 1. Asli Zindagi ka Example (The Airport Security Guard) 👮‍♂️

Socho aap ek **Airport Security Guard** ho.
Aapke paas do checklists hain:
1. **Item Checklist (Individual Expense Rule):**
   - Agar bag me *Liquid* hai $\rightarrow$ 100ml se zyada nahi hona chahiye.
   - Agar *Cigarette lighter* hai $\rightarrow$ Allowed hi nahi hai.
   - Chahe koi bhi item ho $\rightarrow$ 5kg se bhari akele nahi honi chahiye (Global rule).
2. **Passenger Bag Total Checklist (Trip Rule):**
   - Pure bag ka total weight 15kg se zyada nahi hona chahiye.

`SimpleRuleEngine` theek yahi security guard hai!

---

## 2. Har Method aur Return Type ka "Kyu aur Kya"

Pehle in 3 confusing methods aur unke return types ko dimaag me baitha lo:

### A. `rule.check(expense)` ka return type `Optional<Violation>` kyu hai?
Socho agar aap ek kharcha check kar rahe ho:
* **Case 1 (Chori pakdi gayi / Rule toota):** Restaurant bill \$100 aaya jabki limit \$75 thi. Guard bolega: *"Bhai, ye lo violation notice!"* $\rightarrow$ `Optional.of(Violation)`
* **Case 2 (Sab theek hai):** Restaurant bill \$30 aaya. Koi rule nahi toota. Toh guard kya dega? **Kuch nahi!** $\rightarrow$ `Optional.empty()`

Agar return type sirf `Violation` hota, toh rule na tootne par hume `null` return karna padta. Aur Java me `null` aate hi `NullPointerException` ka darr rehta hai.
Isliye Java 8 ne **`Optional<Violation>`** banaya:
> *"Is dabba (Optional) ke andar violation ka paper ho bhi sakta hai, ya dabba khaali (`empty`) bhi ho sakta hai."*

---

### B. Yeh line kya kar rahi hai? `violation.ifPresent(violations::add)`
Yeh Java ka modern shortcut hai. Iska simple sa matlab hai:

```java
// Jo aap normally sochte ho:
if (violation.isPresent()) {
    violations.add(violation.get());
}

// Java 8 ka smart tarika:
violation.ifPresent(violations::add);
```
Matlab: *"Agar dabba khaali nahi hai (violation mila hai), toh us violation ko hamari main list me add kar do. Agar dabba khaali hai, toh aage badho, kuch mat karo."*

---

### C. Yeh line kya kar rahi hai? `expensesByTrip.computeIfAbsent(...)`
```java
expensesByTrip
    .computeIfAbsent(expense.getTripId(), k -> new ArrayList<>())
    .add(expense);
```
Socho aapke paas bohot saare bills pade hain. Aap unhe **Trip ke hisaab se folders** me daal rahe ho:
- **`computeIfAbsent(tripId, k -> new ArrayList<>())`** ka matlab:
  *"Pehle dekho kya is Trip ID ka folder (List) pehle se bana hua hai? Agar NAHI bana, toh naya khaali folder (`new ArrayList<>()`) bana do."*
- **`.add(expense)`** ka matlab:
  *"Aur ab is bill ko us folder ke andar daal do!"*

Bina is method ke aapko 5-6 lines ka lamba `if (map.get(id) == null) { map.put(id, new ArrayList()); }` likhna padta.

---

## 3. Line-by-Line Code Execution: Real Data ke saath Trace Karte Hain! 🔍

Maan lo ek employee ne **2 expenses** submit kiye:
* **Expense 1:** ID = `"E1"`, Trip = `"TRIP-101"`, Type = `RESTAURANT`, Amount = `$100.0`
* **Expense 2:** ID = `"E2"`, Trip = `"TRIP-101"`, Type = `CAB`, Amount = `$30.0`

Aur hamare paas ye rules registered hain:
* `RESTAURANT` $\rightarrow$ Max \$75
* Global rule $\rightarrow$ Max \$200
* Trip rule $\rightarrow$ Trip total max \$110

Aao code ke loop me chalte hain:

```java
List<Violation> violations = new ArrayList<>();
```
👉 Ek khaali list banayi jisme saari galtiyan collect karenge: `violations = []`.

---

### Loop Round 1: Expense 1 (`E1`: RESTAURANT, $100)

```java
// Line 34:
List<ExpenseRule> typeRules = expenseRulesRegistry.get(expense.getExpenseType());
```
1. `expense.getExpenseType()` ne diya $\rightarrow$ `RESTAURANT`.
2. Map se poocha: *"Bhai RESTAURANT ke rules do!"*
3. Map ne diya: `[ MaxAmountRule(75) ]` (List mili!).

```java
// Line 36-39:
for (ExpenseRule rule : typeRules) {
    Optional<Violation> violation = rule.check(expense);
    violation.ifPresent(violations::add);
}
```
1. `rule.check(E1)` chala.
2. Rule ne dekha: Amount \$100 hai jo \$75 se bada hai! ❌
3. Usne return kiya: `Optional.of(Violation("Amount greater than 75"))`.
4. `ifPresent` chala $\rightarrow$ Yeh violation `violations` list me add ho gaya!

```java
// Line 44-49: Global Rules Check
for (ExpenseRule rule : allExpenseRulesRegistry) {
    Optional<Violation> violation = rule.check(expense);
    violation.ifPresent(violations::add);
}
```
1. Global rule chala: `MaxAmountRule(200)`.
2. Amount \$100 hai, jo \$200 se chota hai. Sab theek hai! ✅
3. Rule ne return kiya: `Optional.empty()`.
4. `ifPresent` ne kuch add nahi kiya.

---

### Loop Round 2: Expense 2 (`E2`: CAB, $30)

```java
List<ExpenseRule> typeRules = expenseRulesRegistry.get(ExpenseType.CAB);
```
1. Map se poocha: *"CAB ka koi specific rule hai?"*
2. Map ne return kiya: `null` (kyunki CAB ke liye koi rule banaya hi nahi tha!).
3. `if (typeRules != null)` FALSE ho gaya $\rightarrow$ Loop skip!

```java
// Global Rule check
```
1. Global rule (\$200 max) chala.
2. Amount \$30 hai $\rightarrow$ `Optional.empty()` $\rightarrow$ Pass! ✅

---

### Part 2: Trip-Level Rules (Grouping & Checking)

Ab saare individual bills check ho chuke hain. Ab **Trip total** check karne ki baari hai:

```java
// Line 54-59: Grouping by Trip ID
Map<String, List<Expense>> expensesByTrip = new HashMap<>();
for (Expense expense : expenses) {
    expensesByTrip
        .computeIfAbsent(expense.getTripId(), k -> new ArrayList<>())
        .add(expense);
}
```
Is loop ke baad `expensesByTrip` map aisa ban gaya:
* Key: `"TRIP-101"`
* Value: `[ E1 ($100), E2 ($30) ]`

```java
// Line 61-66: Evaluate Trip Rules
for (List<Expense> tripExpenses : expensesByTrip.values()) {
    for (TripRule tripRule : tripRulesRegistry) {
        Optional<Violation> violation = tripRule.check(tripExpenses);
        violation.ifPresent(violations::add);
    }
}
```
1. Trip rule ko list mili: `[ E1 ($100), E2 ($30) ]`.
2. Trip rule ne sum kiya: $\$100 + \$30 = \$130$.
3. Trip limit thi: \$110.
4. Total \$130 > \$110 $\rightarrow$ Rule toota! ❌
5. Return kiya: `Optional.of(Violation("Trip total exceeds 110"))`.
6. Violation list me add ho gaya!

---

### Final Return (Line 69):
```java
return violations;
```
Engine ne 2 violations pakad ke wapas lauta diye:
1. `E1 exceeded Restaurant limit $75`
2. `TRIP-101 total exceeded $110`

---

## 4. Summary Cheat Sheet: Dimaag me flow kaise yaad rakhein?

```text
[Input: List of Expenses]
         │
         ├── Step 1: For each Expense:
         │     ├── 1A. Category Rule check  --> check(expense) --> Optional<Violation>
         │     └── 1B. Global Rule check    --> check(expense) --> Optional<Violation>
         │
         ├── Step 2: Group expenses by TripId (Folder banana)
         │
         └── Step 3: For each Trip Folder:
               └── Check Trip Rules         --> check(tripExpenses) --> Optional<Violation>
         │
[Output: List of all Violations]
```

Ab batayein, kya ab har method aur loop ka role bilkul natural lag raha hai?
 */