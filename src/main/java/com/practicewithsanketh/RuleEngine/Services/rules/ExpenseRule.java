package com.practicewithsanketh.RuleEngine.Services.rules;

import com.practicewithsanketh.RuleEngine.Models.Expense;

import java.util.Optional;

public interface ExpenseRule {
    Optional<Violation> check(Expense expense);
}
