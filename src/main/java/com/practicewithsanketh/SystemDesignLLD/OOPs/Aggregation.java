package com.practicewithsanketh.SystemDesignLLD.OOPs;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * 2. AGGREGATION ("Has-A" with Independent Lifecycle / WEAK OWNERSHIP)
 * ============================================================================
 * 
 * 🧠 THE INTUITION:
 * - A parent container "has" child objects.
 * - BUT the parent does NOT create or control the lifecycle of the child!
 * - Child objects are created OUTSIDE and passed into the parent.
 * - 🔥 THE ACID TEST: If the Parent is destroyed, DO THE CHILDREN DIE?
 *   --> NO! The children continue to exist outside!
 * 
 * 🛒 REAL-WORLD LLD USE CASE: Amazon Shopping Cart & Products
 * - An Amazon user creates a "ShoppingCart".
 * - The user adds an "iPhone" and "MacBook" from the catalog into the cart.
 * - If the user clicks "Clear Cart" or logs out (Cart destroyed),
 *   does the iPhone get deleted from the Amazon database? OF COURSE NOT!
 * - The Product exists independently in inventory. The Cart merely aggregates references.
 */

// Child Entity: Exists independently in warehouse / catalog
class Product {
    private final String productId;
    private final String title;
    private final double price;

    public Product(String productId, String title, double price) {
        this.productId = productId;
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }
}

// Parent Aggregate: Holds references to products, but does NOT own their lifecycle
class ShoppingCart {
    private final String cartId;
    // Aggregation: List of products created elsewhere
    private final List<Product> items = new ArrayList<>();

    public ShoppingCart(String cartId) {
        this.cartId = cartId;
    }

    // Products are passed from outside (Aggregation!)
    public void addProduct(Product product) {
        items.add(product);
        System.out.println("🛒 Added '" + product.getTitle() + "' to Cart [" + cartId + "]");
    }

    public void checkout() {
        double total = items.stream().mapToDouble(Product::getPrice).sum();
        System.out.println("💳 Checkout Cart [" + cartId + "] with total: $" + total);
    }
}

public class Aggregation {
    public static void main(String[] args) {
        System.out.println("=== REAL-WORLD LLD: AGGREGATION DEMO ===");

        // 1. Products are created independently in the catalog
        Product laptop = new Product("PROD-01", "MacBook Pro", 1999.0);
        Product mouse = new Product("PROD-02", "Logitech MX Master", 99.0);

        // 2. Cart aggregates the products
        ShoppingCart cart = new ShoppingCart("CART-USER-99");
        cart.addProduct(laptop);
        cart.addProduct(mouse);
        cart.checkout();

        // 3. User abandons/deletes the cart
        System.out.println("\n💥 User abandons cart. Cart object is destroyed!");
        cart = null;

        // 4. THE ACID TEST: Are products still alive? YES!
        System.out.println("📦 Products still exist in warehouse catalog:");
        System.out.println("   - " + laptop.getTitle() + " ($" + laptop.getPrice() + ")");
        System.out.println("   - " + mouse.getTitle() + " ($" + mouse.getPrice() + ")");
    }
}

