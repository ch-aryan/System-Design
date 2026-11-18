//package com.practicewithsanketh.RuleEngine.practice;
//
//import java.util.List;
//import java.util.Optional;
//
//public class TotalTripRule implements TripRule{
//
//    private final double amountUsed;
//
//
//    @Override
//    public Optional<Violation> evaluate(List<Expense> expenses) {
//        if(expenses.isEmpty()){
//            return
//        }
//        String tripId = expenses.get(0).getTripId();
//        for(Expense expense:expenses){
//            if(!expense.getTripId().equals(tripId)){
//                return
//            }
//        }
//    }
//}
