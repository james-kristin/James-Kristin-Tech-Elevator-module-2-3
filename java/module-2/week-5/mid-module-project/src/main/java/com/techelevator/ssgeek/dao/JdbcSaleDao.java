package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.Product;
import com.techelevator.ssgeek.model.Sale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcSaleDao implements SaleDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcSaleDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Sale getSale(int saleId) {
        Sale sale = null;
        String sql = "SELECT sale_id, s.customer_id, name, sale_date, ship_date " +
                "FROM sale s " +
                "JOIN customer c ON c.customer_id = s.customer_id " +
                "WHERE sale_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, saleId);
        if (results.next()) {
            sale = mapRowToSale(results);
        }
        return sale;
    }

    @Override
    public List<Sale> getSalesUnshipped() {
        List<Sale> unshippedSales = new ArrayList<>();
        String sql = "SELECT sale_id, s.customer_id, name, sale_date, ship_date " +
                "FROM sale s " +
                "JOIN customer c ON c.customer_id = s.customer_id " +
                "WHERE ship_date IS null " +
                "ORDER BY sale_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Sale sale = mapRowToSale(results);
            unshippedSales.add(sale);
        }
        return unshippedSales;
    }

    @Override
    public List<Sale> getSalesByCustomerId(int customerId) {
        List<Sale> customerSales = new ArrayList<>();
        String sql = "SELECT s.sale_id, s.customer_id, c.name, sale_date, ship_date " +
                "FROM sale s " +
                "JOIN customer c ON c.customer_id = s.customer_id " +
                "WHERE s.customer_id = ? " +
                "ORDER BY sale_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, customerId);
        while (results.next()) {
            Sale sale = mapRowToSale(results);
            customerSales.add(sale);
        }
        return customerSales;
    }

    @Override
    public List<Sale> getSalesByProductId(int productId) {
        List<Sale> productSales = new ArrayList<>();
        String sql = "SELECT s.sale_id, li.product_id, s.customer_id, c.name, sale_date, ship_date " +
                "FROM sale s " +
                "JOIN customer c ON c.customer_id = s.customer_id " +
                "JOIN line_item li ON li.sale_id = s.sale_id " +
                "WHERE li.product_id = ? " +
                "ORDER BY s.sale_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productId);
        while (results.next()) {
            Sale sale = mapRowToSale(results);
            productSales.add(sale);
        }
        return productSales;
    }

    @Override
    public Sale createSale(Sale newSale) {
        String sql = "INSERT INTO sale (customer_id, sale_date, ship_date) " +
                "VALUES (?, ?, ?) Returning sale_id";
        int newId = jdbcTemplate.queryForObject(sql, int.class, newSale.getCustomerId(), newSale.getSaleDate(), newSale.getShipDate());

        return getSale(newId);
    }

    @Override
    public void updateSale(Sale updatedSale) {
        String sql = "UPDATE sale " +
                "SET customer_id = ?, sale_date = ?, ship_date = ? " +
                "WHERE sale_id = ?";
        jdbcTemplate.update(sql, updatedSale.getCustomerId(), updatedSale.getSaleDate(), updatedSale.getShipDate(), updatedSale.getSaleId());
    }

    @Override
    public void deleteSale(int saleId) {

         String sql = "DELETE from line_item WHERE sale_id = ?; " +
         "DELETE from sale WHERE sale_id = ?";
        jdbcTemplate.update(sql, saleId, saleId);
    }

    private Sale mapRowToSale(SqlRowSet results) {
        Sale sale = new Sale();
        sale.setSaleId(results.getInt("sale_id"));
        sale.setCustomerId(results.getInt("customer_id"));
        sale.setSaleDate(LocalDate.parse(results.getString("sale_date")));
        if (results.getString("ship_date") != null) {
            sale.setShipDate(LocalDate.parse(results.getString("ship_date")));
        }
        sale.setCustomerName(results.getString("name"));
        return sale;
    }
}
