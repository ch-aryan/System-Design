package com.securitypractice.practicewithsanketh.Creational.Factory;
 interface vehicle1{
    void start();
    void stop();
}
 class Car1 implements vehicle1{
    public void start(){
        System.out.println("Car started");
    }
    public void stop(){
        System.out.println("Car stopped");
    }
}
class Truck1 implements vehicle1{
    public void start(){
        System.out.println("Truck started");
    }
    public void stop(){
        System.out.println("Truck stopped");
    }
}
class Bike1 implements vehicle1{
    public void start(){
        System.out.println("Bike started");
    }
    public void stop(){
        System.out.println("Bike stopped");
    }
}
class vehicleFactory{
     public static vehicle1 getVehicle(String type){
         if(type.equals("car")){
             return new Car1();
         }else if(type.equals("truck")){
             return new Truck1();
         }else if(type.equals("bike")){
             return new Bike1();
         }
         return null;
     }
}
public class Factory1 {
    public static void main(String[] args) {
        vehicle1 vehicle = vehicleFactory.getVehicle("car");
        vehicle.start();
        vehicle.stop();

        vehicle = vehicleFactory.getVehicle("truck");
        vehicle.start();
        vehicle.stop();

        vehicle = vehicleFactory.getVehicle("bike");
        vehicle.start();
        vehicle.stop();


    }
}
/*
he Savior: Factory Design Pattern
Now, let’s introduce the Factory Design Pattern to rescue us.
🦸‍♀️ The Factory Pattern will allow us to handle the object creation in a
centralized manner, so that we don't need to keep repeating the logic of
choosing which vehicle to create in multiple places.


The Factory Design Pattern is named after a "factory" because, just like a
factory produces different types of products, the pattern provides a central
place (the factory) to create objects of different types. 🏭 Instead of directly
instantiating objects, the factory method is responsible for producing the
correct object, making the system more flexible and organized.


🏭 Solving the Problem with Factory Design Pattern
Here’s how we can solve this problem by introducing a Factory that creates the
 vehicles:
 */
