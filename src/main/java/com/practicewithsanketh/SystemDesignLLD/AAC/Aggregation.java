package com.practicewithsanketh.SystemDesignLLD.AAC;
class Engine{
    private final int a;
    private final int b;
    private final int c;
    Engine(){
        a=8; b = 10; c = 12;
    }
}
class Car{
    private Engine engine;
    protected Car(){
        engine = new Engine();
    }
}
public class Aggregation {
}
