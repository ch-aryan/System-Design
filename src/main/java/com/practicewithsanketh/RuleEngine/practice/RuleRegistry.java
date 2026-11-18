package com.practicewithsanketh.RuleEngine.practice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleRegistry {
    public static Map<ExpenseType, List<ExpenseRule>> getExpenseRuleRegistry(){
        Map<ExpenseType, List<ExpenseRule>> registry = new HashMap<>();

        registry.put(ExpenseType.AIRFARE, List.of(new DisAllowedRule()));
        registry.put(ExpenseType.ENTERTAINMENT, List.of(new MaxAmountRule(50)) );

        return registry;
    }
    public static List<ExpenseRule> getExpenseRulesRegistry(){
        return List.of(new MaxAmountRule(75));
    }
}
