package com.securitypractice.practicewithsanketh.Creational.Factory;
interface Vehicle{
    void start();
    void stop();
}
class Car implements Vehicle{
    public void start(){
        System.out.println("Car started");
    }
    public void stop(){
        System.out.println("Car stopped");
    }
}
class Bike implements Vehicle{
    public void start(){
        System.out.println("Bike started");
    }
    public void stop(){
        System.out.println("Bike stopped");
    }
}
class Truck implements Vehicle{
    public void start(){
        System.out.println("Truck started");
    }
    public void stop(){
        System.out.println("Truck stopped");
    }
}
public class Factory {
    public static void main(String[] args) {
        System.out.println("Factory Pattern");
        Vehicle car = new Car();
        Vehicle bike = new Bike();
        Vehicle truck = new Truck();
        car.start();
        bike.start();
        truck.start();
        car.stop();
        bike.stop();
        truck.stop();
    }
    //another main can be like this.
   /* This code is fragile. If we want to add another vehicle type, we need to modify
    this code again, which is error-prone and hard to maintain. 😖*/
    public class Main {
        public static void main(String[] args) {
            String vehicleType = "Truck"; // Imagine this value is dynamic
            Vehicle vehicle;
            if (vehicleType.equals("Car")) {
                vehicle = new Car();
            } else if (vehicleType.equals("Truck")) {
                vehicle = new Truck();
            } else if (vehicleType.equals("Bike")) {
                vehicle = new Bike();
            } else {
                throw new IllegalArgumentException("Unknown vehicle type");
            }
            vehicle.start();
            vehicle.stop();
        }
    }
}
/*
Here, the Main class creates each vehicle explicitly by calling the constructor of the respective vehicle class. But what if we need to add more vehicle types later, or if we need to change the way vehicles are created? 🤷‍♂️


🤔 Interviewer’s Follow-up Questions: Can We Improve the Code?
An interviewer might ask:

• What if we need to add more vehicle types in the future? 🚙

• What if the logic of vehicle creation changes? 🔧



In this case, the code could become harder to maintain as you add more vehicle types or change the vehicle creation logic. For example, if you had to introduce new behavior or properties for vehicle creation, you would need
 to modify the creation code in many places, which could lead to potential errors. 😱

‍

🛠️ Ugly Code: When We Realize the Code Needs Restructuring
Let’s say, instead of creating vehicles directly, the vehicle creation process is now complex. For example, you have to choose the vehicle based on user input, configuration files, or network requests. If you don’t address this early on, the object creation code quickly becomes cumbersome and ugly. 🤦‍♂️
 */