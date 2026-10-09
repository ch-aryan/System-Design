package com.securitypractice.practicewithsanketh.Behavioral.Strategy;

// PaymentStrategy interface (defines the common method for all payment types)
 interface PaymentStrategy1 {
    void processPayment(); // Abstract method for processing payments
}
class CreditCardPayment1 implements PaymentStrategy1 {
    public void processPayment() {
        System.out.println("Processing Credit Card payment...");
    }
}
class PayPalPayment1 implements PaymentStrategy1 {
    public void processPayment() {
        System.out.println("Processing PayPal payment...");
    }
}
class CryptoPayment1 implements PaymentStrategy1 {
    public void processPayment() {
        System.out.println("Processing Crypto payment...");
    }
}
class StripePayment1 implements PaymentStrategy1 {
    public void processPayment() {
        System.out.println("Processing Stripe payment...");
    }
}
class PaymentProcessor2 {
     PaymentStrategy1 paymentStrategy;
     public PaymentProcessor2(PaymentStrategy1 paymentStrategy) {
         this.paymentStrategy = paymentStrategy;
     }
     public void processPayment() {
         paymentStrategy.processPayment();
     }

    public void setPaymentStrategy(PaymentStrategy1 paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
}
public class Strategy1 {
     public static void main(String[] args) {
         PaymentProcessor2 paymentProcessor = new PaymentProcessor2(new CreditCardPayment1());
         paymentProcessor.processPayment();

         paymentProcessor.setPaymentStrategy(new PayPalPayment1());
         paymentProcessor.processPayment();
     }
}
