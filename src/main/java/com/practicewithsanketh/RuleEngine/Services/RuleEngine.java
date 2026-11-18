package com.practicewithsanketh.RuleEngine.Services;

import com.practicewithsanketh.RuleEngine.Models.Expense;
import com.practicewithsanketh.RuleEngine.Models.ExpenseType;
import com.practicewithsanketh.RuleEngine.Services.rules.Violation;
import com.practicewithsanketh.RuleEngine.Services.rules.*;

import java.util.List;
import java.util.Map;

public interface RuleEngine {
    List<Violation> evaluate(List<Expense> expenses, // incoming expenses
                             Map<ExpenseType, List<ExpenseRule>> expenseRulesRegistry,// expense rules registry
                             List<ExpenseRule> allExpenseRulesRegistry, // all expense rules registry
                             List<TripRule> tripRulesRegistry);//trip rules registry

}
/*
Spot on! You have nailed the core concept.

In Low-Level Design (LLD), an **Orchestrator** is indeed the component responsible for
coordinating other classes and executing a process step-by-step.

---

### The Simple Analogy: A Restaurant Manager

Think of an orchestrator like a **restaurant floor manager**.

* The manager doesn't cook the food (that's the `KitchenService`).
* The manager doesn't mix the drinks (that's the `BarService`).
* The manager doesn't wash the dishes (that's the `DishwashingService`).

Instead, the manager **coordinates** everyone: they take the order, pass it to the kitchen at the
right time, tell the waiter when the food is ready, and ensure the customer gets their bill.
The orchestrator controls the *flow* and *sequence* of events.

---

### A Real Coding Example: E-Commerce Checkout

Imagine a user clicks **"Place Order"**. If you didn't use an orchestrator,
your `Cart` class would become a messy nightmare trying to talk to the inventory,
payment gateway, shipping provider, and email service all at once.

Instead, you create an **`OrderOrchestrator`** class. Its only job is to run these steps in order:

1. **Step 1:** Call `InventoryService` to check if items are in stock.
2. **Step 2:** Call `PaymentService` to charge the customer's credit card.
3. **Step 3:** Call `ShippingService` to generate a tracking label.
4. **Step 4:** Call `NotificationService` to send a confirmation email.

If Step 2 (Payment) fails, the orchestrator handles it (e.g., cancels the inventory hold) and stops the process.

---

### Why Do We Use an Orchestrator?

* **Separation of Concerns:** Individual classes (like payment or inventory) only focus on *how* to do their specific job, while the orchestrator focuses on *when* things happen.
* **Single Source of Truth for Workflow:** If you ever need to change the order of steps (e.g., checking inventory *after* processing payment), you only have to change it in one place—the orchestrator.
* **Easier Error Handling:** It acts like a traffic controller, making it easy to see what went wrong and where the process failed.

---

Would you like to walk through a quick code example (like in Java or Python) to see how an orchestrator looks compared to normal code?
 */