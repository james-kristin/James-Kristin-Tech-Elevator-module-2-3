package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.Customer;
import com.techelevator.ssgeek.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;

public class JdbcProductDaoTests extends BaseDaoTests{

    private static final Product PRODUCT_1 = new Product(1, "Product 1", "Description 1",   new BigDecimal("9.99"), "product-1.png");
    private static final Product PRODUCT_2 = new Product(2, "Product 2", "Description 2",   new BigDecimal("19.00"), "product-2.png");
    private static final Product PRODUCT_3 = new Product(3, "Product 3", "Description 3",   new BigDecimal("123.45"), "product-3.png");
    private static final Product PRODUCT_4 = new Product(4, "Product 4", "Description 4",   new BigDecimal("0.99"), "product-4.png");

    private JdbcProductDao productDao;
    private Product testProduct;

    @Before
    public void setup() {
        productDao = new JdbcProductDao(dataSource);
        testProduct = new Product(0, "Test Product", "Test Description", new BigDecimal("0.00"), "test-product.png");
    }

    @Test
    public void getProduct_returns_correct_product_for_id() {
        Product product = productDao.getProduct(1);
        Assert.assertNotNull(product);
        assertProductsMatch(PRODUCT_1, product);

        product = productDao.getProduct(4);
        Assert.assertNotNull(product);
        assertProductsMatch(PRODUCT_4, product);
    }

    @Test
    public void getProduct_returns_null_when_id_not_found() {
        Product product = productDao.getProduct(307);
        Assert.assertNull(product);
    }

    @Test
    public void getProducts_returns_list_of_all_products() {
        List<Product> allProducts = productDao.getProducts();
        Assert.assertEquals(4, allProducts.size());
        assertProductsMatch(PRODUCT_1, allProducts.get(0));
        assertProductsMatch(PRODUCT_2, allProducts.get(1));
        assertProductsMatch(PRODUCT_3, allProducts.get(2));
        assertProductsMatch(PRODUCT_4, allProducts.get(3));
    }

    @Test
    public void getProductsWithNoSales_returns_list_of_products_without_sales() {
        List<Product> allProducts = productDao.getProductsWithNoSales();
        Assert.assertEquals(1, allProducts.size());
        assertProductsMatch(PRODUCT_3, allProducts.get(0));

    }

    @Test
    public void createProduct_returns_product_with_id_and_expected_values() {
        Product createdProduct = productDao.createProduct(testProduct);

        Assert.assertNotNull(createdProduct);

        int newId = createdProduct.getProductId();
        Assert.assertTrue( newId > 0);

        testProduct.setProductId(newId);
        assertProductsMatch( testProduct, createdProduct);
    }

    @Test
    public void created_product_has_expected_values_when_retrieved() {
        Product createdProduct = productDao.createProduct(testProduct);

        int newId = createdProduct.getProductId();
        Product retrievedProduct = productDao.getProduct(newId);

        assertProductsMatch(createdProduct, retrievedProduct);
    }

    @Test
    public void updated_customer_has_expected_values_when_retrieved() {
        Product product = productDao.getProduct(1);
        product.setName("A new name");
        product.setDescription("new Description");
        product.setPrice(new BigDecimal("20000.00"));
        product.setImageName("new_image.png");

        productDao.updateProduct(product);

        Product updatedProduct = productDao.getProduct(1);
        assertProductsMatch(product, updatedProduct);
    }

    @Test
    public void deleted_product_cant_be_retrieved() {
        productDao.deleteProduct(3);

        Product product = productDao.getProduct(3);
        Assert.assertNull(product);

        List<Product> products = productDao.getProducts();
        Assert.assertEquals(3, products.size());
    }

    @Test
    public void product_with_sales_cannot_be_deleted() {

        try {
            productDao.deleteProduct(1);
        } catch (DataIntegrityViolationException exception) {
            return;
        }
        Assert.fail("A product with sales should not be deleted.");
    }

    private void assertProductsMatch(Product expected, Product actual) {
        Assert.assertEquals(expected.getProductId(), actual.getProductId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
        Assert.assertEquals(expected.getImageName(), actual.getImageName());
    }
}
