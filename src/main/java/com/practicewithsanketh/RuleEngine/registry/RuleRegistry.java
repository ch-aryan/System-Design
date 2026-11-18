package com.practicewithsanketh.RuleEngine.registry;


import com.practicewithsanketh.RuleEngine.Models.ExpenseType;
import com.practicewithsanketh.RuleEngine.Services.impl.DisallowRule;
import com.practicewithsanketh.RuleEngine.Services.impl.MaxAmountRule;
import com.practicewithsanketh.RuleEngine.Services.impl.TripTotalMaxRule;
import com.practicewithsanketh.RuleEngine.Services.rules.ExpenseRule;
import com.practicewithsanketh.RuleEngine.Services.rules.TripRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RuleRegistry {

    public static Map<ExpenseType, List<ExpenseRule>> getExpenseRulesRegistry() {
        Map<ExpenseType, List<ExpenseRule>> registry = new HashMap<>();

        registry.put(ExpenseType.RESTAURANT, List.of(
                new MaxAmountRule(75)

        ) );

        registry.put(ExpenseType.AIRFARE, List.of(
                new DisallowRule()
        ) );

        return registry;

    }

    public static List<ExpenseRule> getAllExpenseRulesRegistry() {
        return List.of(
                new MaxAmountRule(200)
        );
    }

    public static List<TripRule> getAllTripRulesRegistry() {
        return List.of(
                new TripTotalMaxRule(1000)
        );
    }
}


/**
 *
 *
 * represent the above riles like a json config
 *
 * {
 *   "expenseLeveRules": [
 *     {
 *       "expenseType": "RESTAURANT",
 *       "rules": [
 *         {
 *           "type": "DISALLOW",
 *           "maxAmount": 75
 *         }
 *       ]
 *     }
 *   ],
 *   "allExpenseRules": [
 *     {
 *       "type": "MAX_AMOUNT",
 *       "maxAmount": 200
 *     }
 *   ]
 * }
 */