package com.practicewithsanketh.RuleEngine;

import com.practicewithsanketh.RuleEngine.Models.Expense;
import com.practicewithsanketh.RuleEngine.Models.ExpenseType;
import com.practicewithsanketh.RuleEngine.Services.RuleEngine;
import com.practicewithsanketh.RuleEngine.Services.impl.DisallowRule;
import com.practicewithsanketh.RuleEngine.Services.impl.MaxAmountRule;
import com.practicewithsanketh.RuleEngine.Services.impl.SimpleRuleEngine;
import com.practicewithsanketh.RuleEngine.Services.impl.TripTotalMaxRule;
import com.practicewithsanketh.RuleEngine.Services.rules.ExpenseRule;
import com.practicewithsanketh.RuleEngine.Services.rules.TripRule;
import com.practicewithsanketh.RuleEngine.Services.rules.Violation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

// ============================================================================
// STAGE 2: CONFIG DTOs (Data Transfer Objects mapped from JSON / YAML)
// ============================================================================
class RuleConfigDTO {
    public String ruleType; // "MAX_AMOUNT", "DISALLOW", "TRIP_TOTAL_MAX"
    public Double amount;

    public RuleConfigDTO(String ruleType, Double amount) {
        this.ruleType = ruleType;
        this.amount = amount;
    }
}

class CategoryConfigDTO {
    public ExpenseType expenseType;
    public List<RuleConfigDTO> rules = new ArrayList<>();

    public CategoryConfigDTO(ExpenseType expenseType, List<RuleConfigDTO> rules) {
        this.expenseType = expenseType;
        this.rules = rules;
    }
}

class PolicyConfigSnapshot {
    public List<CategoryConfigDTO> categoryRules = new ArrayList<>();
    public List<RuleConfigDTO> globalRules = new ArrayList<>();
    public List<RuleConfigDTO> tripRules = new ArrayList<>();
}

// ============================================================================
// STAGE 3: FACTORY (Instantiates concrete Rule objects from DTO definitions)
// ============================================================================
class RuleFactory {
    public static ExpenseRule createExpenseRule(RuleConfigDTO config) {
        switch (config.ruleType) {
            case "MAX_AMOUNT":
                return new MaxAmountRule(config.amount);
            case "DISALLOW":
                return new DisallowRule();
            default:
                throw new IllegalArgumentException("Unknown expense rule: " + config.ruleType);
        }
    }

    public static TripRule createTripRule(RuleConfigDTO config) {
        switch (config.ruleType) {
            case "TRIP_TOTAL_MAX":
                return new TripTotalMaxRule(config.amount);
            default:
                throw new IllegalArgumentException("Unknown trip rule: " + config.ruleType);
        }
    }
}

// ============================================================================
// STAGE 3 (Contd): THREAD-SAFE DYNAMIC REGISTRY (Zero-Downtime Hot Reload)
// ============================================================================
class DynamicRuleRegistry {

    public static class ActiveRuleBook {
        public final Map<ExpenseType, List<ExpenseRule>> categoryRules;
        public final List<ExpenseRule> globalRules;
        public final List<TripRule> tripRules;

        public ActiveRuleBook(Map<ExpenseType, List<ExpenseRule>> categoryRules,
                              List<ExpenseRule> globalRules,
                              List<TripRule> tripRules) {
            this.categoryRules = Collections.unmodifiableMap(categoryRules);
            this.globalRules = Collections.unmodifiableList(globalRules);
            this.tripRules = Collections.unmodifiableList(tripRules);
        }
    }

    // Atomic reference ensures thread-safe instant swap without server restart!
    private final AtomicReference<ActiveRuleBook> ruleBookRef = new AtomicReference<>();

    public void updatePolicy(PolicyConfigSnapshot snapshot) {
        Map<ExpenseType, List<ExpenseRule>> newCategoryRules = new HashMap<>();

        for (CategoryConfigDTO cat : snapshot.categoryRules) {
            List<ExpenseRule> rules = new ArrayList<>();
            for (RuleConfigDTO r : cat.rules) {
                rules.add(RuleFactory.createExpenseRule(r));
            }
            newCategoryRules.put(cat.expenseType, rules);
        }

        List<ExpenseRule> newGlobalRules = new ArrayList<>();
        for (RuleConfigDTO r : snapshot.globalRules) {
            newGlobalRules.add(RuleFactory.createExpenseRule(r));
        }

        List<TripRule> newTripRules = new ArrayList<>();
        for (RuleConfigDTO r : snapshot.tripRules) {
            newTripRules.add(RuleFactory.createTripRule(r));
        }

        // ATOMIC SWAP: Old rules replaced with new rules in 0.001 milliseconds
        ActiveRuleBook newBook = new ActiveRuleBook(newCategoryRules, newGlobalRules, newTripRules);
        ruleBookRef.set(newBook);
    }

    public ActiveRuleBook getActiveRuleBook() {
        return ruleBookRef.get();
    }
}

// ============================================================================
// PRODUCTION SIMULATION RUNNER
// ============================================================================
public class ProductionRuleEngineDemo {

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("🚀 SIMULATING PRODUCTION DYNAMIC RULE ENGINE (UBER FLIPR STYLE)");
        System.out.println("===============================================================\n");

        DynamicRuleRegistry dynamicRegistry = new DynamicRuleRegistry();
        RuleEngine engine = new SimpleRuleEngine();

        // --------------------------------------------------------------------
        // Step 1: Initial Config arrives from Config Service (Uber Flipr / JSON)
        // Manager set: Restaurant limit = $75.0
        // --------------------------------------------------------------------
        System.out.println("📥 [Config Service] Loading Policy v1.0 from Uber Flipr / JSON...");
        PolicyConfigSnapshot policyV1 = new PolicyConfigSnapshot();
        policyV1.categoryRules.add(new CategoryConfigDTO(
                ExpenseType.RESTAURANT,
                List.of(new RuleConfigDTO("MAX_AMOUNT", 75.0))
        ));
        policyV1.globalRules.add(new RuleConfigDTO("MAX_AMOUNT", 200.0));
        policyV1.tripRules.add(new RuleConfigDTO("TRIP_TOTAL_MAX", 1000.0));

        dynamicRegistry.updatePolicy(policyV1);
        System.out.println("✅ Policy v1.0 activated in memory! Restaurant Limit: $75.0\n");

        // --------------------------------------------------------------------
        // Step 2: Employee submits an $80 restaurant bill
        // --------------------------------------------------------------------
        Expense lunchBill = new Expense("EXP-101", "TRIP-1", 80.0, ExpenseType.RESTAURANT);
        System.out.println("👤 Employee submits: " + lunchBill.getExpenseType() + " expense of $" + lunchBill.getAmountUsed());

        DynamicRuleRegistry.ActiveRuleBook activeBook = dynamicRegistry.getActiveRuleBook();
        List<Violation> violations = engine.evaluate(
                List.of(lunchBill),
                activeBook.categoryRules,
                activeBook.globalRules,
                activeBook.tripRules
        );

        if (violations.isEmpty()) {
            System.out.println("🎉 Result: APPROVED!");
        } else {
            System.out.println("❌ Result: REJECTED! Reason: " + violations.get(0).getMessage());
        }

        // --------------------------------------------------------------------
        // Step 3: HOT RELOAD!
        // Finance Manager updates limit to $100.0 on Admin UI (NO SERVER RESTART)
        // --------------------------------------------------------------------
        System.out.println("\n---------------------------------------------------------------");
        System.out.println("🔔 [HOT RELOAD EVENT] Admin updated Restaurant limit to $100.0 on UI!");
        System.out.println("⚡ Pushing new JSON config to live server (Zero downtime)...");
        System.out.println("---------------------------------------------------------------");

        PolicyConfigSnapshot policyV2 = new PolicyConfigSnapshot();
        policyV2.categoryRules.add(new CategoryConfigDTO(
                ExpenseType.RESTAURANT,
                List.of(new RuleConfigDTO("MAX_AMOUNT", 100.0)) // <-- Updated limit!
        ));
        policyV2.globalRules.add(new RuleConfigDTO("MAX_AMOUNT", 200.0));
        policyV2.tripRules.add(new RuleConfigDTO("TRIP_TOTAL_MAX", 1000.0));

        dynamicRegistry.updatePolicy(policyV2);
        System.out.println("✅ Policy v2.0 live in memory! New Restaurant Limit: $100.0\n");

        // --------------------------------------------------------------------
        // Step 4: Employee re-submits the exact same $80 bill
        // --------------------------------------------------------------------
        System.out.println("👤 Employee re-submits: " + lunchBill.getExpenseType() + " expense of $" + lunchBill.getAmountUsed());

        activeBook = dynamicRegistry.getActiveRuleBook();
        violations = engine.evaluate(
                List.of(lunchBill),
                activeBook.categoryRules,
                activeBook.globalRules,
                activeBook.tripRules
        );

        if (violations.isEmpty()) {
            System.out.println("🎉 Result: APPROVED! (Allowed under new Policy v2.0 without restarting server!)");
        } else {
            System.out.println("❌ Result: REJECTED! Reason: " + violations.get(0).getMessage());
        }

        System.out.println("\n===============================================================");
        System.out.println("🏆 This is how high-scale production systems operate!");
        System.out.println("===============================================================");
    }
}

