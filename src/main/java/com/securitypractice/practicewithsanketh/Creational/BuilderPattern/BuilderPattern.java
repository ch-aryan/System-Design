package com.securitypractice.practicewithsanketh.Creational.BuilderPattern;
//withoutBuilder
public class BuilderPattern {
    private Integer id;
    private String name;
    private int age;
    private String address;
    private String phoneNumber;
    private String email;
    private Education education;
    private String password;
    BuilderPattern(){
        System.out.println("hello this is Zero paramterized constructor constructor");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BuilderPattern(Integer id, String name, String address, int age, String phoneNumber, String email, Education education, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.education = education;
        this.password = password;
    }
}
class Education {
    private String degree;
    private String university;
    private String year;

    public Education(String degree, String university, String year) {
        this.degree = degree;
        this.university = university;
        this.year = year;
    }
}
/* let us understand the builder pattern */
class WithoutBuilderPattern{
    BuilderPattern builderPattern = new BuilderPattern
            (1, "Sanketh", "Bangalore", 25, "9876543210", "sanketh@example.com", new Education("B.Tech", "XYZ University", "2019"), "password123");
}
/*
Problems here:

You can't tell which value maps to which field (is "Bangalore" the name or address?)
If you only need 3 fields, you still must pass all 8 (or create multiple constructors)
Easy to accidentally swap parameters of the same type
 (e.g., name and address are both String)
 */