package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.LineItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;
import java.util.List;

public class JdbcLineItemDaoTests extends BaseDaoTests{

    private static final LineItem LINE_ITEM_1 = new LineItem(1, 1, 1, 1, "Product 1", new BigDecimal("9.99"));
    private static final LineItem LINE_ITEM_2 = new LineItem(2, 1, 2, 1, "Product 2", new BigDecimal("19.00"));
    private static final LineItem LINE_ITEM_3 = new LineItem(3, 1, 4, 1, "Product 4", new BigDecimal("0.99"));

    private JdbcLineItemDao lineItemDao;


    @Before
    public void setup() {
        lineItemDao = new JdbcLineItemDao(dataSource);
    }

    @Test
    public void getLineItemBySale_returns_list_of_all_line_items_for_sale_id() {
        List<LineItem> testList = lineItemDao.getLineItemsBySale(1);
        Assert.assertEquals(3, testList.size());
        assertLineItemsMatch(LINE_ITEM_1, testList.get(0));
        assertLineItemsMatch(LINE_ITEM_2, testList.get(1));
        assertLineItemsMatch(LINE_ITEM_3, testList.get(2));
    }

    private void assertLineItemsMatch(LineItem expected, LineItem actual) {
        Assert.assertEquals(expected.getLineItemId(), actual.getLineItemId());
        Assert.assertEquals(expected.getSaleId(), actual.getSaleId());
        Assert.assertEquals(expected.getProductId(), actual.getProductId());
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
        Assert.assertEquals(expected.getProductName(), actual.getProductName());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
    }
}
