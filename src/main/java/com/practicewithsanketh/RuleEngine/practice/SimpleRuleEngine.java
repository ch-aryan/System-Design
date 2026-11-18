package com.practicewithsanketh.RuleEngine.practice;

import java.util.List;
import java.util.Map;

public interface SimpleRuleEngine {
    List<Violation> evalue(List<Expense> expenses, Map<ExpenseType, List<ExpenseRule>> expenseRuleMap,
                        List<ExpenseRule> allExpenseRulesRegistry, List<TripRule> tripRules   );
}
