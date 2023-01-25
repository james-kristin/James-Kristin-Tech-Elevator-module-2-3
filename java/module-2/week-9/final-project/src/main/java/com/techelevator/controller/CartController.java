package com.techelevator.controller;

import com.techelevator.dao.CartDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.model.Tax;
import com.techelevator.model.User;
import com.techelevator.service.TaxService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "cart")
@PreAuthorize("isAuthenticated()")
public class CartController {

    private CartDao cartDao;
    private TaxService taxService;
    private UserDao userDao;

    public CartController(CartDao cartDao, TaxService taxService, UserDao userDao) {
        this.cartDao = cartDao;
        this.taxService = taxService;
        this.userDao = userDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public Cart getCart(Principal principal) {
        Cart cart = new Cart();
        List<CartItem> cartItems = cartDao.getCart();
        cart.setCartItems(cartItems);
        User user = userDao.findByUsername(principal.getName());
        cart.setTaxRate(taxService.getTaxRate(user));
        cart.setSubtotal(cart.getSubtotal(cartItems));
        cart.setFinalTotal(cart.getFinalTotal(cart.getSubtotal(), cart.getTaxRate()));
        return cart;

    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/items", method = RequestMethod.POST)
    public Cart add(@RequestBody CartItem cartItem) {
        Cart cart = new Cart();
        cart.setCartItems(cartDao.getCart());
        cartDao.add(cart, cartItem);
        return cart;

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/items/{itemId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int itemId) {
        cartDao.delete(itemId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public void deleteCart() {
        cartDao.delete();
    }
}
