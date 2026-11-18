package com.practicewithsanketh.RuleEngine.practice;

public class Expense {
    private final String id;
    private final String tripId;
    private final double amtUsed;
    private final ExpenseType type;

    public Expense(String id, String tripId, double amtUsed, ExpenseType type) {
        this.id = id;
        this.tripId = tripId;
        this.amtUsed = amtUsed;
        this.type = type;
    }

    public String getTripId() {
        return tripId;
    }

    public String getId() {
        return id;
    }

    public double getAmtUsed() {
        return amtUsed;
    }

    public ExpenseType getType() {
        return type;
    }
}
enum ExpenseType{
    RESTAURANT, AIRFARE, ENTERTAINMENT;
}
