package com.practicewithsanketh.RuleEngine.Models;

public class Expense {
    private final String expenseId;
    private final String tripId;
    private final Double amountUsed;
    private ExpenseType expenseType;

    public Expense(String expenseId, String tripId, Double amountUsed, ExpenseType expenseType) {
        this.expenseId = expenseId;
        this.tripId = tripId;
        this.amountUsed = amountUsed;
        this.expenseType = expenseType;
    }
    //getters

    public String getExpenseId() {
        return expenseId;
    }

    public String getTripId() {
        return tripId;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public Double getAmountUsed() {
        return amountUsed;
    }
}
