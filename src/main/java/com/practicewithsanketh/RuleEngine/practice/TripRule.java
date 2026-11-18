package com.practicewithsanketh.RuleEngine.practice;

import java.util.List;
import java.util.Optional;

public interface TripRule {
    Optional<Violation> evaluate(List<Expense> expenses);
}
