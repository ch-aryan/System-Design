package com.practicewithsanketh.RuleEngine.Services.impl;



import com.practicewithsanketh.RuleEngine.Models.Expense;
import com.practicewithsanketh.RuleEngine.Services.rules.ExpenseRule;
import com.practicewithsanketh.RuleEngine.Services.rules.Violation;

import java.util.Optional;



public class MaxAmountRule implements ExpenseRule {

    private final double maxAmount;

    public MaxAmountRule(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public Optional<Violation> check(Expense e) {
        if (e.getAmountUsed() > maxAmount) {
            return Optional.of(Violation.of("Expense amount is greater than the max amount" + " id:" + e.getExpenseId() ));
        }

        return Optional.empty();
    }
}

/**
 *
 * class ParseRule {
 *
 *   public static void parseRule(Rule rule) {
 *
 *       switch (rule.getOperator()) {
 *         case "equals":
 *           return rule.getFieldKey().equals(rule.getFieldValue());
 *         case "notEquals":
 *           return !rule.getFieldKey().equals(rule.getFieldValue());
 *         case "lessThan":
 *           return rule.getFieldKey() < rule.getFieldValue();
 *
 *   }
 *
 * }
 *
 * {
 *   "condition": [{
 *    "fieldKey": "companyName",
 *    "fieldValue": "Google",
 *    "operator": "equals"
 *
 *  },  {
 *     "fieldKey": "department",
 *     "fieldValue": "Engineering",
 *     "operator": "equals"
 *  }, {
 *    "fieldKey": "expenseType",
 *    "fieldValue": ["AIRFARE", "HOTEL"]
 *    "operator": "equals",
 *    "expenses": {
 *       "airfare": {
 *
 *        }
 *      }
 *   }, {
 *
 *
 * }],
 *
 *  "violation": {
 *    "message": "Expense amount is greater than the max amount"
 *  }
 *
 * }
 *
 * }
 *
 */