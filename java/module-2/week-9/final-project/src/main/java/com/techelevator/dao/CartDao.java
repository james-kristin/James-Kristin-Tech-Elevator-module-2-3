package com.techelevator.dao;


import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;

import java.util.List;

public interface CartDao {

    //Get the user's cart
    List<CartItem> getCart();

    //Add item to the user's cart
    Cart add(Cart currentCart, CartItem newCartItem);

    //Delete an item from the user's cart
    void delete(int cartItemId);

    //Delete the entire user's cart
    void delete();
}
