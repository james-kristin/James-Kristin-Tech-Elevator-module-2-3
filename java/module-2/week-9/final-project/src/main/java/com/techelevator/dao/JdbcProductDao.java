package com.techelevator.dao;

import com.techelevator.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao{

    private final JdbcTemplate jdbcTemplate;
    public JdbcProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " +
                "FROM product";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Product product = mapRowToProduct(results);
            products.add(product);
        }
        return products;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> filteredProducts = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " +
                "FROM product " +
                "WHERE name LIKE '%' || ? || '%'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
        while (results.next()) {
            Product product = mapRowToProduct(results);
            filteredProducts.add(product);
        }
        return filteredProducts;
    }

    @Override
    public List<Product> getProductsBySku(String productSku) {
        List<Product> filteredProducts = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name  " +
                "FROM product " +
                "WHERE product_sku LIKE '%' || ? || '%'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productSku);
        while (results.next()) {
            Product product = mapRowToProduct(results);
            filteredProducts.add(product);
        }
        return filteredProducts;
    }

    @Override
    public Product getProduct(int productId) {
        Product product = null;
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " +
                "FROM product " +
                "WHERE product_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productId);
        if (results.next()) {
            product = mapRowToProduct(results);
        }
        return product;
    }

    private Product mapRowToProduct(SqlRowSet results) {
        Product product = new Product();
        product.setProductId(results.getInt("product_id"));
        product.setProductSku(results.getString("product_sku"));
        product.setName(results.getString("name"));
        product.setDescription(results.getString("description"));
        product.setPrice(results.getBigDecimal("price"));
        product.setImageName(results.getString("image_name"));
        return product;
    }
}
