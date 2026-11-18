package com.practicewithsanketh.Behavioral.Strategy;

class PaymentProcessor {
    // This method will process payment based on payment method type
    public void processPayment(String paymentMethod) {
        if (paymentMethod.equals("CreditCard")) {
            // Process Credit Card payment
            System.out.println("Processing credit card payment...");
        } else if (paymentMethod.equals("PayPal")) {
            // Process PayPal payment
            System.out.println("Processing PayPal payment...");
        } else if (paymentMethod.equals("Crypto")) {
            // Process Crypto payment
            System.out.println("Processing crypto payment...");
        } else {
            // If an unsupported payment method is entered
            System.out.println("Payment method not supported.");
        }
    }
}
//slight improvement.
 interface PaymentMethod {
     void processPayment(); // Abstract method for processing payments
 }
 class CreditCardPayment implements PaymentMethod {
    public void processPayment() {
        System.out.println("Processing credit card payment...");
    }
}

 class PayPalPayment implements PaymentMethod {
    public void processPayment() {
        System.out.println("Processing PayPal payment...");
    }
}

 class CryptoPayment implements PaymentMethod {
    public void processPayment() {
        System.out.println("Processing crypto payment...");
    }
}

 class StripePayment implements PaymentMethod {
    public void processPayment() {
        System.out.println("Processing Stripe payment...");
    }
}
class PaymentProcessor1 {
    // This method processes payment based on the payment method type
    public void processPayment(String paymentMethod) {
        if (paymentMethod.equals("CreditCard")) {
            CreditCardPayment creditCard = new CreditCardPayment();
            creditCard.processPayment(); // Process Credit Card payment
        } else if (paymentMethod.equals("PayPal")) {
            PayPalPayment payPal = new PayPalPayment();
            payPal.processPayment(); // Process PayPal payment
        } else if (paymentMethod.equals("Crypto")) {
            CryptoPayment crypto = new CryptoPayment();
            crypto.processPayment(); // Process Crypto payment
        } else if (paymentMethod.equals("Stripe")) {
            StripePayment stripe = new StripePayment();
            stripe.processPayment(); // Process Stripe payment
        } else {
            System.out.println("Payment method not supported.");
        }
    }
}
public class Strategy {
}
