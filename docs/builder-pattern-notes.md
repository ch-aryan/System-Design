# Builder Pattern

## 1. Why do we need Builder?

Suppose we have a `User` object with many attributes:

-   `firstName`
-   `lastName`
-   `age`
-   `email`
-   `password`
-   and many more

Some fields may be required, some optional, and some validations may
depend on multiple fields.

The first approach is to keep fields public. But then anyone can modify
the object directly, so validation cannot be reliably enforced.

So we make fields private and provide setters.

### Problem with setters

The object is already created before all validations are completed.

``` java
User user = new User();
user.setFirstName(...);
user.setAge(...);
user.setEmail(...);
```

The object can exist in an invalid or partially constructed state.

We could validate everything in the constructor.

### Problem with a constructor

With many attributes, the constructor becomes difficult to use:

``` java
new User(firstName, lastName, age, email, password, ...);
```

Problems:

-   Too many parameters
-   Parameter order matters
-   Hard to read
-   Optional values may require `null`
-   Adding parameters makes the constructor harder to maintain
-   Multiple constructors can lead to constructor overloading complexity

So the problem is:

> **How can we create a complex object step-by-step, keep construction
> readable, validate it properly, and produce a valid final object?**

------------------------------------------------------------------------

## 2. Builder Pattern --- the idea

The **Builder Pattern** separates the process of constructing an object
from the final object itself.

The builder temporarily holds the values.

``` text
Client
  ↓
Builder
  ↓  set values / validate
build()
  ↓
Final Product
```

The builder is used for construction; the final product remains
controlled and can be immutable.

``` java
User user = User.builder()
        .age(27)
        .firstName("Sanket")
        .lastName("Singh")
        .email("...")
        .build();
```

The important idea is:

> **Builder is not the final object. It is a temporary object used to
> construct the final object.**

------------------------------------------------------------------------

## 3. How it solves the problems

### Readability

Instead of:

``` java
new User("Sanket", "Singh", 27, "...", ...);
```

we get:

``` java
User.builder()
    .firstName("Sanket")
    .lastName("Singh")
    .age(27)
    .email("...")
    .build();
```

The meaning of each value is clear.

### Optional fields

We only provide the fields we need. The builder can keep defaults for
optional fields.

### Validation

Validation can happen while values are added and/or when `build()` is
called.

Most importantly, `build()` is the point where the final object is
created, so invalid combinations can prevent object creation.

### Immutability

The final `User` can have:

-   private fields
-   no public setters
-   final fields
-   validation completed before creation

So after `build()`, the created object should not be casually modified.

------------------------------------------------------------------------

## 4. Why not setters?

``` java
User user = new User();
user.setAge(27);
user.setEmail("...");
```

The problem is that the object already exists while we are still
constructing it.

Builder gives us:

``` text
Builder → incomplete/temporary
              ↓
            build()
              ↓
       valid final object
```

This makes the construction boundary explicit.

------------------------------------------------------------------------

## 5. Why not constructor?

A constructor works well when the object has only a few parameters.

With many parameters:

``` java
new User(a, b, c, d, e, f, g, h, ...);
```

the code becomes difficult to understand and maintain.

Builder provides named, readable steps:

``` java
User.builder()
    .firstName(...)
    .age(...)
    .email(...)
    .build();
```

Optional parameters do not have to be passed just to satisfy constructor
order.

------------------------------------------------------------------------

## 6. The core structure

``` java
class User {

    private final String firstName;
    private final String lastName;
    private final int age;
    private final String email;

    private User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.email = builder.email;
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String firstName;
        private String lastName;
        private int age;
        private String email;

        Builder firstName(String value) {
            // validation
            this.firstName = value;
            return this;
        }

        Builder age(int value) {
            // validation
            this.age = value;
            return this;
        }

        User build() {
            return new User(this);
        }
    }
}
```

The important relationship is:

``` text
Builder
  │
  │ contains values temporarily
  ↓
User constructor
  │
  ↓
Immutable User
```

------------------------------------------------------------------------

## 7. Fluent API

Builder methods return `this`.

``` java
Builder age(int value) {
    this.age = value;
    return this;
}
```

Therefore calls can be chained:

``` java
User.builder()
    .age(27)
    .firstName("Sanket")
    .lastName("Singh")
    .build();
```

This is called a **fluent interface**.

------------------------------------------------------------------------

## 8. Builder in the assignment

The assignment uses two products: `Itinerary` and `HttpRequest`. Both
have required and optional fields, validation, and immutable final
objects. fileciteturn0file0L2-L22

For example, `Itinerary` has required fields such as traveler name,
dates, origin and destination, while segments, tags, budget and
insurance are optional/defaulted. fileciteturn0file0L7-L18

The final object must not expose setters, and collections should be
protected using read-only views or defensive copies.
fileciteturn0file0L19-L22

The expected usage is intentionally readable:

``` java
Itinerary.builder()
    .travelerName("Alice")
    .origin("LHR")
    .destination("CDG")
    .startDate(...)
    .endDate(...)
    .build();
```

The assignment also makes `build()` responsible for rejecting missing or
invalid required values with clear errors. fileciteturn0file0L23-L31

------------------------------------------------------------------------

## 9. The mental model

Do not remember Builder as:

> "A pattern for creating objects."

Remember the problem:

``` text
Many attributes
      +
Required + optional fields
      +
Validation
      +
Readable construction
      +
Avoid telescoping constructors
      +
Want a controlled/immutable final object
      ↓
Builder Pattern
```

### One-line takeaway

> **Builder is useful when object creation itself becomes complicated;
> it moves the complexity of construction into a separate, readable
> builder and creates the final object only through `build()`.**
