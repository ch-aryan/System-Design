package com.practicewithsanketh.RuleEngine;



import com.practicewithsanketh.RuleEngine.Models.Expense;
import com.practicewithsanketh.RuleEngine.Models.ExpenseType;
import com.practicewithsanketh.RuleEngine.Services.RuleEngine;
import com.practicewithsanketh.RuleEngine.Services.rules.ExpenseRule;
import com.practicewithsanketh.RuleEngine.Services.rules.TripRule;
import com.practicewithsanketh.RuleEngine.Services.rules.Violation;
import com.practicewithsanketh.RuleEngine.registry.RuleRegistry;

import java.util.List;
import java.util.Map;


public class RuleManagerRunner {

    private final RuleEngine ruleEngine;

    public RuleManagerRunner(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    public void run(List<Expense> expenses) {
        Map<ExpenseType, List<ExpenseRule>> expenseRulesRegistry = RuleRegistry.getExpenseRulesRegistry();
        List<ExpenseRule> allExpenseRulesRegistry = RuleRegistry.getAllExpenseRulesRegistry();
        List<TripRule> tripRulesRegistry = RuleRegistry.getAllTripRulesRegistry();

        List<Violation> violations = ruleEngine.evaluate(expenses,
                expenseRulesRegistry, allExpenseRulesRegistry, tripRulesRegistry);

        for(Violation violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}