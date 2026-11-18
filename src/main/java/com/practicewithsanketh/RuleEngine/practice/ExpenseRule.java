package com.practicewithsanketh.RuleEngine.practice;

import java.util.Optional;

public interface ExpenseRule {

    Optional<Violation> check(Expense expense);
}
