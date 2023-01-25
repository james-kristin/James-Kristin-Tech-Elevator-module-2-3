package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> cartItems;
    private BigDecimal subtotal;
    private BigDecimal taxRate;
    private BigDecimal finalTotal;

    public Cart() {
        subtotal = new BigDecimal(0);
    }



    public Cart(List<CartItem> cartItems, BigDecimal subtotal, BigDecimal taxRate, BigDecimal finalTotal) {
        this.cartItems = cartItems;
        this.subtotal = subtotal;
        this.taxRate = taxRate;
        this.finalTotal = finalTotal;
    }


    public List<CartItem> addItemToCart(CartItem cartItem) {
        cartItems.add(cartItem);
        return cartItems;
    }

    public BigDecimal getSubtotal(List<CartItem> cartItems) {

        for (CartItem cartItem : cartItems) {
            BigDecimal price = cartItem.getPrice();
            int quantity = cartItem.getQuantity();
            BigDecimal bigQuantity = new BigDecimal(quantity);
            BigDecimal itemQuantityPrice = price.multiply(bigQuantity);
            subtotal = subtotal.add(itemQuantityPrice);

        }
        return subtotal;
    }

    public BigDecimal getFinalTotal(BigDecimal subtotal, BigDecimal taxRate) {
        finalTotal = subtotal.add((subtotal.multiply(taxRate)));
        return finalTotal;
    }

    public BigDecimal getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(BigDecimal finalTotal) {
        this.finalTotal = finalTotal;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }


    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
