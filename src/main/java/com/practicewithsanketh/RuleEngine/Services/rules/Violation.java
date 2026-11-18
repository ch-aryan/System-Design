package com.practicewithsanketh.RuleEngine.Services.rules;

import com.practicewithsanketh.RuleEngine.Models.Expense;

public class Violation {
    private final String message;
   private  Violation(String message){
        this.message = message;
    }
    public static Violation of(String message){
        return new Violation(message);
    }
    public String getMessage(){
        return message;
    }
}
/*
This pattern combines two foundational Java design concepts: a **Value Object** and a **Static Factory Method** (Famously Item 1 in Joshua Bloch’s *Effective Java*).

To make it feel completely natural—so you write it by instinct rather than memory—let’s break down the **three exact design decisions** happening in those 12 lines of code.

---

## 1. Why `private final String message;`? (The Value Object Mindset)

Think about what a `Violation` actually represents in a Rule Engine. Once a rule executes and finds a problem (e.g., `"Candidate age must be at least 21"`), that violation is a **historical fact** about that evaluation run.

* Facts shouldn't mutate mid-flight while being passed across services or logged.
* By marking `message` as `final` and providing no setter, you make `Violation` **immutable**. It is completely thread-safe and predictable.

---

## 2. Why Lock the Constructor (`private Violation(...)`) and Use `public static Violation of(...)`?

When we first learn Java, we are taught to create objects using `new Violation("...")`. Why go out of our way to block `new` and force callers to write `Violation.of("...")`?

Think of a `public` constructor like leaving the back door of a restaurant kitchen unlocked: anyone can walk in and assemble a plate however they want. Making the constructor `private` locks the kitchen door,
 and `public static Violation of(...)` becomes the **service counter**. Every caller *must* go through your controlled entry point.

Using a **Static Factory Method** (`of`) instead of a `public` constructor gives you four concrete engineering superpowers:

### A. Expressive, Domain-Driven Readability

When reading Rule Engine logic, which line reads more like clean English?

* **Constructor:** `return new Violation("Income below threshold");` *(Focuses on memory allocation: "allocate a new object")*
* **Static Factory:** `return Violation.of("Income below threshold");` *(Reads like a domain sentence: "return a Violation of 'Income below threshold'")*

You already use this pattern every day in modern Java without realizing it:

* `List.of("A", "B")`
* `LocalDate.of(2026, 10, 29)`
* `Optional.of(profile)`

### B. You Can Have Multiple Descriptive Names for the Same Parameter Types

Suppose tomorrow your Rule Engine needs two ways to create a `Violation` from a `String`: one from a raw message, and one from an error code lookup.
With constructors, **you are stuck**—Java does not allow two constructors with the exact same `String` signature:

```java
// COMPILER ERROR: Duplicate constructor Violation(String)
public Violation(String message) { ... }
public Violation(String errorCode) { ... }

```

Because you locked the constructor, you can effortlessly add named static factories that make the caller's intent 100% obvious:

```java
public static Violation ofMessage(String message) {
    return new Violation(message);
}

public static Violation fromErrorCode(String code) {
    String lookedUpMsg = ErrorDictionary.resolve(code);
    return new Violation(lookedUpMsg);
}

```

### C. Memory Caching / Object Reuse Without Breaking Callers

A `new` constructor is **forced by the JVM specification** to allocate a brand-new object on the Heap every single time it is called.

In a high-throughput Rule Engine evaluating 10,000 requests per second, what if 8,000 of them pass all rules (`NO_VIOLATION`) or trigger the exact same standard rule failure? Because callers use `Violation.of(...)` instead of `new`, you can later optimize memory under the hood (Flyweight caching) **without changing a single line of caller code**:

```java
public class Violation {
    private static final Violation NONE = new Violation("NONE");
    private final String message;

    private Violation(String message) {
        this.message = message;
    }

    public static Violation of(String message) {
        if ("NONE".equals(message)) {
            return NONE; // Returns cached singleton! Zero heap allocation.
        }
        return new Violation(message);
    }
}

```

If callers had written `new Violation(...)` across 50 files, you could never introduce this optimization without refactoring the entire codebase.

### D. Returning Subtypes Later (Polymorphism)

A constructor can *only* ever return the exact class `Violation`. A static method like `Violation.of(...)` can evolve in the future to return a subclass (like `CriticalViolation` or `WarningViolation`) dynamically based on the input parameters, keeping the implementation classes completely hidden from the caller.

---

## 3. How to Build the Intuition (The Mental Trigger)

Whenever you are about to type `public ClassName(...)` in an LLD problem or backend project, pause for two seconds and ask yourself:

1. **Is this a small domain value or result wrapper?** (e.g., `Violation`, `RuleResult`, `Money`, `EmailAddress`, `ApiResponse`).
2. **Will it make the calling code read like natural English if I use `.of(...)`, `.from(...)`, or `.success(...)`?**
3. **Might I want to validate, cache, or add alternative creation methods with the same parameter types later?**

If the answer is yes:

1. Make the fields `private final`.
2. Make the constructor `private` (so nobody bypasses your gatekeeper).
3. Expose a `public static` method (`of`, `from`, `empty`, etc.) as the single front door.

### Standard Java Naming Conventions for Static Factories

Once you start using this pattern, stick to the universal Java vocabulary so other engineers immediately understand your code:

* **`of(...)`**: Simple aggregation or wrapper around given value(s) — `Violation.of("Invalid PAN")`, `Set.of(1, 2)`.
* **`from(...)`**: Type-conversion taking another object and extracting data — `Violation.from(exception)`.
* **`valueOf(...)`**: Verbose conversion, often parsing strings/primitives — `BigInteger.valueOf(100L)`.
* **`empty()` / `none()**`: Returns a pre-cached, zero-allocation empty instance — `Violation.none()`.

> **Modern Java 17/21 Bonus:** If your class is purely an immutable data carrier and you still want the `.of(...)` static factory syntax, you can also write it as a one-line Java `record`:
> ```java
> public record Violation(String message) {
>     public static Violation of(String message) {
>         return new Violation(message);
>     }
> }
>
> ```
>
>
> A `record` automatically gives you `private final` fields, `message()` getter, `equals()`, `hashCode()`, and `toString()`, though its canonical constructor remains `public`. Use the classic class with a `private` constructor when you strictly want to forbid `new Violation(...)` outside the class.
 */