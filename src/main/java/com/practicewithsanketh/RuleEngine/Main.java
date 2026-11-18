package com.practicewithsanketh.RuleEngine;

import com.practicewithsanketh.RuleEngine.Models.Expense;
import com.practicewithsanketh.RuleEngine.Models.ExpenseType;
import com.practicewithsanketh.RuleEngine.Services.RuleEngine;
import com.practicewithsanketh.RuleEngine.Services.impl.SimpleRuleEngine;

import java.util.ArrayList;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        

        List<Expense> expenses = new ArrayList<>();


        expenses.add(new Expense("1", "1", 10.0, ExpenseType.RESTAURANT));
        expenses.add(new Expense("2", "1", 50.0, ExpenseType.RESTAURANT));
        expenses.add(new Expense("3", "1", 100.0, ExpenseType.RESTAURANT));
        expenses.add(new Expense("4", "1", 100.0, ExpenseType.AIRFARE));

        RuleManagerRunner ruleManagerRunner = new RuleManagerRunner(new SimpleRuleEngine());

        ruleManagerRunner.run(expenses);

    }
}