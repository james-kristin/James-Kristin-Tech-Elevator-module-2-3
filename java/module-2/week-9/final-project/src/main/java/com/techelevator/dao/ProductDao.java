package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {

    // Get a list of all products
    List<Product> getProducts();

    // Get a list of products by name
    List<Product> getProductsByName(String name);

    // Get a list of products by SKU
    List<Product> getProductsBySku(String productSku);

    //  Get a specific product by id
    Product getProduct(int productId);
}
