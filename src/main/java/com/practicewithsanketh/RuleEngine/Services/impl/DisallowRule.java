package com.practicewithsanketh.RuleEngine.Services.impl;



import java.util.Optional;

import com.practicewithsanketh.RuleEngine.Models.Expense;
import com.practicewithsanketh.RuleEngine.Services.rules.ExpenseRule;
import com.practicewithsanketh.RuleEngine.Services.rules.Violation;

public class DisallowRule implements ExpenseRule {

    @Override
    public Optional<Violation> check(Expense e) {
        return Optional.of(Violation.of("Expense type " + e.getExpenseType() + "id:" + e.getExpenseId() + " is not allowed"));
    }
}