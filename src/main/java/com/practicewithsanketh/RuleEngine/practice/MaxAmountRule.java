package com.practicewithsanketh.RuleEngine.practice;

import java.util.Optional;

public class MaxAmountRule implements ExpenseRule{

    private final double amt;

    MaxAmountRule(double amt){
        this.amt = amt;
    }

    @Override
    public Optional<Violation> check(Expense expense) {
       if(expense.getAmtUsed()> amt){
           return Optional.of(Violation.of("you have spent amount greater than the rule"));
       }
       return Optional.empty();
    }

}
class DisAllowedRule implements ExpenseRule{

    @Override
    public Optional<Violation> check(Expense expense) {
        return Optional.of(Violation.of(expense.getId() + "  " + expense.getType() + " is not allowed"));
    }
}
