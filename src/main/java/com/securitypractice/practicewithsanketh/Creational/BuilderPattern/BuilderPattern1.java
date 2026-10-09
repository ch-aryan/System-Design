package com.securitypractice.practicewithsanketh.Creational.BuilderPattern;

public class BuilderPattern1 {
    private Integer id;
    private String name;
    private int age;
    private String address;
    private String phoneNumber;
    private String email;
    private Education education;
    private String password;

    // Private constructor — only Builder can create instances
    private BuilderPattern1(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.education = builder.education;
        this.password = builder.password;
    }

    // Getters (no setters — object is immutable!)
    public Integer getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public Education getEducation() { return education; }
    public String getPassword() { return password; }

    // Static method to get a new Builder
    public static Builder builder() {
        return new Builder();
    }

    // The Builder class
    public static class Builder {
        private Integer id;
        private String name;
        private int age;
        private String address;
        private String phoneNumber;
        private String email;
        private Education education;
        private String password;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder education(Education education) {
            this.education = education;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        // Final build method
        public BuilderPattern1 build() {
            return new BuilderPattern1(this);
        }
    }
}
class UsingBuilderPattern {
    BuilderPattern1 person = BuilderPattern1.builder()
            .id(1)
            .name("Sanketh")
            .address("Bangalore")
            .age(25)
            .phoneNumber("9876543210")
            .email("sanketh@example.com")
            .education(new Education("B.Tech", "XYZ University", "2019"))
            .password("password123")
            .build();

    // You can also skip optional fields!
    BuilderPattern1 minimalPerson = BuilderPattern1.builder()
            .id(2)
            .name("Aryan")
            .build();
}
/*
See how readable it is? Every value is labeled. You can set only the fields you need.

Step 2: Lombok @Builder — Same Thing in 1 Line
First, add Lombok to your pom.xml:

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
Now your entire class becomes:

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BuilderPattern {
    private Integer id;
    private String name;
    private int age;
    private String address;
    private String phoneNumber;
    private String email;
    private Education education;
    private String password;
}
That's it! Lombok auto-generates the entire Builder inner class, the builder() method, all the setter methods in the builder, and the build() method at compile time. Usage is exactly the same:

BuilderPattern person = BuilderPattern.builder()
        .id(1)
        .name("Sanketh")
        .build();
Why is Lombok helpful?

You wrote ~80 lines of boilerplate builder code manually. Lombok does it with @Builder.
Less code = fewer bugs, easier maintenance.
If you add a new field, Lombok automatically updates the builder. With raw Java, you'd have to manually add a new method in the Builder class.
Step 3: @SuperBuilder — Why Do We Need It?
@Builder does NOT work with inheritance. Consider this:

@Getter
@Builder
public class Person {
    private String name;
    private int age;
}

@Getter
@Builder
public class Student extends Person {
    private String university;
    private String degree;
}
Problem: Student.builder().name("Aryan") — ❌ This WON'T compile! The Student builder doesn't know about name and age from the parent Person class.

Solution — @SuperBuilder:

import lombok.experimental.SuperBuilder;
import lombok.Getter;

@Getter
@SuperBuilder
public class Person {
    private String name;
    private int age;
}

@Getter
@SuperBuilder
public class Student extends Person {
    private String university;
    private String degree;
}
Now this works perfectly:

Student student = Student.builder()
        .name("Aryan")          // from Person (parent)
        .age(22)                // from Person (parent)
        .university("XYZ Uni") // from Student (child)
        .degree("B.Tech")      // from Student (child)
        .build();
Key rules for @SuperBuilder:

Both parent and child classes must have @SuperBuilder (not just one)
You cannot mix @Builder and @SuperBuilder in the same hierarchy
Use @SuperBuilder whenever your class extends another class and you want builder pattern
Quick Summary Table
Feature	Raw Java	@Builder	@SuperBuilder
Boilerplate code	~80+ lines	1 annotation	1 annotation
Inheritance support	Manual (complex)	❌ No	✅ Yes
When to use	Learning purposes	Simple classes	Classes with parent-child hierarchy
 */