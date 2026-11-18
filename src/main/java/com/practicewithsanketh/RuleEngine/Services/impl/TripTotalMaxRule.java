package com.practicewithsanketh.RuleEngine.Services.impl;

import com.practicewithsanketh.RuleEngine.Models.Expense;
import com.practicewithsanketh.RuleEngine.Services.rules.TripRule;
import com.practicewithsanketh.RuleEngine.Services.rules.Violation;
import com.practicewithsanketh.RuleEngine.utils.ExpenseUtils;

import java.util.List;
import java.util.Optional;

public class TripTotalMaxRule implements TripRule {
    private final double maxAmount;

    // constructor
    public TripTotalMaxRule(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public Optional<Violation> check(List<Expense> expenses) {
        if(!ExpenseUtils.areAllExpensesOfSameTrip(expenses)) {
            return Optional.of(Violation.of("Expenses are not of the same trip"));
        }
        double total = 0;
        for(Expense expense : expenses) {
            total += expense.getAmountUsed();
        }
        if(total > maxAmount) {
            return Optional.of(Violation.of("Trip total exceeds the maximum amount"));
        }

        return Optional.empty();
    }
}