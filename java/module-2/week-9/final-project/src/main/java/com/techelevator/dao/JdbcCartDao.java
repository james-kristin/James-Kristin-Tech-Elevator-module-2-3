package com.techelevator.dao;

import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCartDao implements CartDao{

    private final JdbcTemplate jdbcTemplate;
    public JdbcCartDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<CartItem> getCart() {
        List<CartItem> cart = new ArrayList<>();
        String sql = "SELECT c.cart_item_id, c.user_id, c.product_id, c.quantity, p.name, p.price " +
                "FROM cart_item c " +
                "JOIN product p ON c.product_id = p.product_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            CartItem cartItem = mapRowToCartItem(results);
            cart.add(cartItem);
        }
        return cart;
    }

    @Override
    public Cart add(Cart currentCart, CartItem newCartItem) {
        List<CartItem> cartItems = currentCart.getCartItems();

        //cartItems.stream().filter(x-> x.getProductId() == newCartItem.getProductId());

        if (cartItems.contains(newCartItem)) {
            String sql = "UPDATE cart_item " +
                    "SET quantity = (quantity + ?) " +
                    "WHERE product_id = ?";
            jdbcTemplate.update(sql, newCartItem.getQuantity(), newCartItem.getProductId());
            cartItems = getCart();
            currentCart.setCartItems(cartItems);

        } else {
            String sql = "INSERT INTO cart_item (user_id, product_id, quantity) " +
                    "VALUES (?, ?, ?) RETURNING cart_item_id";
            int newId =jdbcTemplate.queryForObject(sql, int.class, newCartItem.getUserId(), newCartItem.getProductId(), newCartItem.getQuantity());
            cartItems.add(newCartItem);
            currentCart.setCartItems(cartItems);

        }

        return currentCart;

    }

    @Override
    public void delete(int cartItemId) {
        String sql = "DELETE FROM cart_item " +
                "WHERE cart_item_id = ?";
        jdbcTemplate.update(sql, cartItemId);
    }

    @Override
    public void delete() {
        String sql = "DELETE FROM cart_item";
        jdbcTemplate.update(sql);
    }


    private CartItem mapRowToCartItem(SqlRowSet results) {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(results.getInt("cart_item_id"));
        cartItem.setUserId(results.getInt("user_id"));
        cartItem.setProductId(results.getInt("product_id"));
        cartItem.setQuantity(results.getInt("quantity"));
        cartItem.setName(results.getString("name"));
        cartItem.setPrice(results.getBigDecimal("price"));

        return cartItem;
    }

}
