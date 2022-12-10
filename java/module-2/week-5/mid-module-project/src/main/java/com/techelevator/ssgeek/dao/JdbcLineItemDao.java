package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.LineItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcLineItemDao implements LineItemDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcLineItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<LineItem> getLineItemsBySale(int saleId) {
        List<LineItem> saleLineItems = new ArrayList<>();
        String sql = "SELECT line_item_id, li.sale_id, p.product_id, p.name, p.price, quantity " +
                "FROM line_item li " +
                "JOIN product p ON p.product_id = li.product_id " +
                "WHERE li.sale_id = ? " +
                "ORDER BY line_item_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, saleId);
        while (results.next()) {
            LineItem lineItem = mapRowToLineItem(results);
            saleLineItems.add(lineItem);
        }
        return saleLineItems;
    }

    private LineItem mapRowToLineItem(SqlRowSet results) {
        LineItem lineItem = new LineItem();
        lineItem.setLineItemId(results.getInt("line_item_id"));
        lineItem.setSaleId(results.getInt("sale_id"));
        lineItem.setProductId(results.getInt("product_id"));
        lineItem.setQuantity(results.getInt("quantity"));
        lineItem.setProductName(results.getString("name"));
        lineItem.setPrice(results.getBigDecimal("price"));
        return lineItem;
    }
}
