# Factory Pattern

## 1. Why do we need a Factory?

Suppose the system needs to create different implementations of the same abstraction:

```text
Vehicle
  ├── Car
  ├── Truck
  └── Bike
```

The client should use `Vehicle`, but somewhere it must decide which concrete class to create.

Without a factory, creation logic gets repeated:

```java
Vehicle vehicle;

if (type.equals("Car")) {
    vehicle = new Car();
} else if (type.equals("Truck")) {
    vehicle = new Truck();
} else if (type.equals("Bike")) {
    vehicle = new Bike();
}
```

If this decision appears in many places, the knowledge of **which concrete class to instantiate** spreads throughout the application.

The problem is therefore not simply `new`.

> **The problem is that object-creation decisions and concrete-class knowledge are leaking into client code.**

---

## 2. What does Factory actually do?

A Factory centralizes the decision of **which concrete object to create**.

```text
Client
  │
  │ asks for Vehicle
  ▼
VehicleFactory
  │
  ├── Car
  ├── Truck
  └── Bike
```

The client becomes:

```java
Vehicle vehicle = VehicleFactory.getVehicle(type);
```

The client knows the abstraction, while the factory owns the creation decision.

The Factory example in the source follows exactly this idea: the factory receives the vehicle type and returns the appropriate `Vehicle` implementation. fileciteturn1file0L196-L205

---

## 3. Why not just use `new`?

Direct construction is completely fine when creation is simple and stable.

```java
Vehicle vehicle = new Car();
```

Do **not** introduce a Factory merely to hide every `new`.

Factory becomes useful when:

- there are multiple implementations;
- the concrete type is selected dynamically;
- creation logic is becoming complicated;
- the same creation decision appears in multiple places;
- the client should not depend on concrete implementations.

The source specifically shows the problem becoming worse when the type comes from dynamic input/configuration and the client accumulates `if/else` creation logic. fileciteturn1file0L162-L192

---

# 4. Factory vs Abstract Factory — the important difference

Your confusion here is valid because the two names sound very similar.

The easiest way to remember them is:

```text
Factory
   → creates ONE kind of product

Abstract Factory
   → creates a FAMILY of related products
```

### Factory

Suppose our system only needs one product category:

```text
Notification
   ├── EmailNotification
   ├── SmsNotification
   └── PushNotification
```

A factory can decide which **Notification** to create.

```text
NotificationFactory
       ↓
 Email / SMS / Push
```

The question is:

> **Which implementation of this product should I create?**

---

## 5. What changes with Abstract Factory?

Now imagine a cross-platform UI system.

We don't need only a Button. We need a whole consistent UI family:

```text
Windows family
 ├── WindowsButton
 ├── WindowsCheckbox
 └── WindowsMenu

Mac family
 ├── MacButton
 ├── MacCheckbox
 └── MacMenu
```

Now the question is not:

> "Which Button should I create?"

It is:

> **"Which entire family of compatible objects should I create?"**

So we have:

```text
UIFactory
   ├── createButton()
   ├── createCheckbox()
   └── createMenu()
```

and concrete factories:

```text
WindowsFactory
 ├── WindowsButton
 ├── WindowsCheckbox
 └── WindowsMenu

MacFactory
 ├── MacButton
 ├── MacCheckbox
 └── MacMenu
```

The client selects one factory and receives a consistent family from it.

---

# 6. So why not always use Abstract Factory?

This is the key to your question.

> **Abstract Factory is not simply a "better Factory".**

It solves a **larger and different problem**.

If you only have:

```text
PaymentMethod
 ├── UPI
 ├── Card
 └── Cash
```

using an Abstract Factory would usually be unnecessary complexity.

You don't have a family of related products. You only have one product category: `PaymentMethod`.

A simple factory is enough.

But if you have:

```text
PaymentFamily
 ├── PaymentProcessor
 ├── RefundProcessor
 └── ReceiptGenerator
```

and each provider has a compatible family:

```text
Razorpay family
 ├── RazorpayPayment
 ├── RazorpayRefund
 └── RazorpayReceipt

Stripe family
 ├── StripePayment
 ├── StripeRefund
 └── StripeReceipt
```

then Abstract Factory starts making sense.

---

# 7. The easiest mental model

Think in terms of **dimensions**.

### One dimension → Factory

```text
Which Notification?
       ↓
 Email / SMS / Push
```

Only one product is being selected.

### Multiple related dimensions → Abstract Factory

```text
Which platform?
       ↓
 Windows family
    ├── Button
    ├── Checkbox
    └── Menu

 Mac family
    ├── Button
    ├── Checkbox
    └── Menu
```

One choice determines multiple related products.

---

# 8. Important correction about the provided notes

The supplied Abstract Factory article presents a `VehicleFactory` that creates only one `Vehicle` product and calls this Abstract Factory. fileciteturn1file1L228-L258

That example is useful for understanding **factory abstraction**, but it does not demonstrate the strongest/core idea of the canonical Abstract Factory pattern: **creating families of related products**.

The article itself later gives the more important distinction: Factory Method focuses on one product, while Abstract Factory focuses on multiple related products. fileciteturn1file1L315-L360

For your own notes, keep the second idea as the main mental model.

---

# 9. When will we actually use them?

### Factory — common situations

- Choosing a payment implementation
- Choosing a notification sender
- Creating a parser based on file/input type
- Creating a storage implementation
- Creating a database/client implementation
- Creating different domain implementations from configuration

### Abstract Factory — common situations

- Cross-platform UI components
- Different database/provider families
- Different cloud-provider families
- Game object families
- Any system where several objects must belong to the same compatible variant/family

---

# 10. Factory in Matrimony OS

A simple Factory could make sense when we have one abstraction with multiple implementations.

For example:

```text
NotificationSender
       │
       ├── WhatsAppSender
       ├── EmailSender
       └── SmsSender
```

The client asks for a `NotificationSender`; the creation/selection decision can be centralized.

Similarly:

```text
PhotoStorage
       │
       ├── S3PhotoStorage
       └── LocalPhotoStorage
```

or:

```text
LLMProvider
       │
       ├── GeminiProvider
       ├── OpenAIProvider
       └── ClaudeProvider
```

These are **one product category with multiple implementations**, so a simple Factory can be enough.

We should not introduce Abstract Factory unless we actually have **families of related implementations** that need to stay consistent.

---

# 11. The decision I should make as an engineer

Do not ask:

> "Can I use Factory here?"

Ask:

> **"Is object creation becoming a responsibility that I should separate from the client?"**

Then ask:

> **"Am I creating one product type, or a family of related products?"**

```text
One product type
      ↓
Factory

Family of related products
      ↓
Abstract Factory
```

### One-line takeaway

> **Factory centralizes the decision of which implementation of a product to create. Abstract Factory goes one level higher and creates a consistent family of related products.**
