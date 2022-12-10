package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.Product;
import com.techelevator.ssgeek.model.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class JdbcSaleDaoTests extends BaseDaoTests{

    private static final Sale SALE_1 = new Sale(1, 1, LocalDate.parse("2022-01-01"), null);
    private static final Sale SALE_2 = new Sale(2, 1, LocalDate.parse("2022-02-01"), LocalDate.parse("2022-02-02"));
    private static final Sale SALE_3 = new Sale(3, 2, LocalDate.parse("2022-03-01"), null);
    private static final Sale SALE_4 = new Sale(4, 2, LocalDate.parse("2022-01-01"), LocalDate.parse("2022-01-02"));

    private JdbcSaleDao saleDao;
    private Sale testSale;

    @Before
    public void setup() {
        saleDao = new JdbcSaleDao(dataSource);
        testSale = new Sale(0, 1, LocalDate.parse("2022-11-11"), null);
    }

    @Test
    public void getSale_returns_correct_sale_for_id() {
        Sale sale = saleDao.getSale(1);
        Assert.assertNotNull(sale);
        assertSalesMatch(SALE_1, sale);

        sale = saleDao.getSale(4);
        Assert.assertNotNull(sale);
        assertSalesMatch(SALE_4, sale);
    }

    @Test
    public void getSale_returns_null_when_id_not_found() {
        Sale sale = saleDao.getSale(-30);
        Assert.assertNull(sale);
    }

    @Test
    public void getSalesUnshipped_returns_list_of_all_sales_not_shipped() {
        List<Sale> unshippedSales = saleDao.getSalesUnshipped();
        Assert.assertEquals(2, unshippedSales.size());
        assertSalesMatch(SALE_1, unshippedSales.get(0));
        assertSalesMatch(SALE_3, unshippedSales.get(1));
    }

    @Test
    public void getSalesByCustomerId_returns_list_of_all_sales_for_customer_id() {
        List<Sale> salesByCustomer = saleDao.getSalesByCustomerId(2);
        Assert.assertEquals(2, salesByCustomer.size());
        assertSalesMatch(SALE_3, salesByCustomer.get(0));
        assertSalesMatch(SALE_4, salesByCustomer.get(1));
    }

    @Test
    public void getSalesByProductId_returns_list_of_all_sales_for_product_id() {
        List<Sale> salesByProduct = saleDao.getSalesByProductId(4);
        Assert.assertEquals(2, salesByProduct.size());
        assertSalesMatch(SALE_1, salesByProduct.get(0));
        assertSalesMatch(SALE_2, salesByProduct.get(1));
    }

    @Test
    public void createSale_returns_Sale_with_id_and_expected_values() {
        Sale createdSale = saleDao.createSale(testSale);

        Assert.assertNotNull(testSale);

        int newId = createdSale.getSaleId();
        Assert.assertTrue( newId > 0);

        testSale.setSaleId(newId);
        assertSalesMatch( testSale, createdSale);
    }

    @Test
    public void created_sale_has_expected_values_when_retrieved() {
        Sale createdSale = saleDao.createSale(testSale);

        int newId = createdSale.getSaleId();
        Sale retrievedSale = saleDao.getSale(newId);

        assertSalesMatch(createdSale, retrievedSale);
    }

    @Test
    public void updated_sale_has_expected_values_when_retrieved() {
        Sale sale = saleDao.getSale(2);
        sale.setCustomerId(3);
        sale.setSaleDate(LocalDate.parse("1999-01-01"));
        sale.setShipDate(null);

        saleDao.updateSale(sale);

        Sale updatedSale = saleDao.getSale(2);
        assertSalesMatch(sale, updatedSale);
    }

    @Test
    public void deleted_sale_cant_be_retrieved() {
        saleDao.deleteSale(3);

        Sale sale = saleDao.getSale(3);
        Assert.assertNull(sale);

        List<Sale> sales = saleDao.getSalesUnshipped();
        Assert.assertEquals(1, sales.size());
    }


    private void assertSalesMatch(Sale expected, Sale actual) {
        Assert.assertEquals(expected.getSaleId(), actual.getSaleId());
        Assert.assertEquals(expected.getCustomerId(), actual.getCustomerId());
        Assert.assertEquals(expected.getSaleDate(), actual.getSaleDate());
        Assert.assertEquals(expected.getShipDate(), actual.getShipDate());
    }
}
