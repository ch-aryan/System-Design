package com.practicewithsanketh.RuleEngine.Services.rules;

import com.practicewithsanketh.RuleEngine.Models.Expense;

import java.util.*;

public interface TripRule {
    Optional<Violation> check(List<Expense> expenses);
}