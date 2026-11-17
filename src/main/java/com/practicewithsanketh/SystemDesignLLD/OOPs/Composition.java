package com.practicewithsanketh.SystemDesignLLD.OOPs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================================
 * 3. COMPOSITION ("Part-Of" with Strict Lifecycle Ownership / STRONG OWNERSHIP)
 * ============================================================================
 * 
 * 🧠 THE INTUITION:
 * - The parent object OWNS the child completely.
 * - The child CANNOT exist without the parent.
 * - The parent creates the child internally (or controls its creation directly).
 * - 🔥 THE ACID TEST: If the Parent is destroyed, DO THE CHILDREN DIE?
 *   --> YES! The children die along with the parent.
 * 
 * 🍔 REAL-WORLD LLD USE CASE: Swiggy/Zomato Order & OrderItem
 * - When you place a Swiggy Order, the order creates "OrderItems"
 *   (e.g. Line 1: "2x Butter Naan @ $3 each", Line 2: "1x Paneer Tikka @ $8").
 * - Can a "Line Item: 2x Butter Naan for Order #402" exist floating in space
 *   without an Order? NO! It has zero meaning without the Order!
 * - If the Order is cancelled/deleted from the database, all its line items
 *   are deleted in cascade with it!
 */

// Child Entity: Strictly a PART of an Order. Cannot exist independently!
class OrderItem {
    private final String itemName;
    private final int quantity;
    private final double unitPrice;

    // Package-private or private: OrderItem should only be created by the Order!
    OrderItem(String itemName, int quantity, double unitPrice) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() {
        return quantity * unitPrice;
    }

    public String getItemDetails() {
        return quantity + "x " + itemName + " ($" + unitPrice + " each) = $" + getSubtotal();
    }
}

// Parent Aggregate Root: Creates and strictly owns OrderItems
class Order {
    private final String orderId;
    // Composition: The Order controls the lifecycle of its items
    private final List<OrderItem> items = new ArrayList<>();

    public Order(String orderId) {
        this.orderId = orderId;
    }

    // Notice: External world passes simple data; Order CREATES the OrderItem internally!
    public void addItem(String itemName, int quantity, double unitPrice) {
        OrderItem item = new OrderItem(itemName, quantity, unitPrice);
        items.add(item);
        System.out.println("➕ Added item to Order [" + orderId + "]: " + item.getItemDetails());
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public void printReceipt() {
        System.out.println("\n🧾 --- ORDER RECEIPT [" + orderId + "] ---");
        for (OrderItem item : items) {
            System.out.println("   " + item.getItemDetails());
        }
        System.out.println("Total Payable: $" + calculateTotal());
    }

    // In a real database/ORM (like JPA / Hibernate):
    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // When order is deleted, all OrderItems are deleted from DB automatically!
}

public class Composition {
    public static void main(String[] args) {
        System.out.println("=== REAL-WORLD LLD: COMPOSITION DEMO ===");

        // 1. Create Order
        Order order = new Order("SWIGGY-ORD-9021");

        // 2. Order creates and encapsulates its items internally
        order.addItem("Paneer Butter Masala", 1, 12.0);
        order.addItem("Butter Naan", 3, 2.5);
        order.printReceipt();

        // 3. What happens if Order is cancelled / garbage collected?
        System.out.println("\n💥 Order [" + "SWIGGY-ORD-9021" + "] is cancelled and deleted from memory!");
        order = null;

        // 4. THE ACID TEST: Can the line items exist now?
        // NO! OrderItem objects were strictly owned by the order.
        // They are garbage collected together with the order.
        System.out.println("💀 All OrderItems died with the Order. No floating line items exist!");
    }
}

