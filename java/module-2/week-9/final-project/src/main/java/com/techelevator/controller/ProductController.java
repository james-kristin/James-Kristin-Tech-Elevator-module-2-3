package com.techelevator.controller;

import com.techelevator.dao.ProductDao;
import com.techelevator.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "products")
public class ProductController {

    private ProductDao productDao;

    public ProductController (ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Product> products(@RequestParam(defaultValue = "") String sku, @RequestParam(defaultValue = "") String name) {
        if (!sku.equals("")) {
            return productDao.getProductsBySku(sku);
        }
        if (!name.equals("")) {
            return productDao.getProductsByName(name);
        }

        return productDao.getProducts();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Product product(@PathVariable int id) {
        Product product = productDao.getProduct(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        } else {
            return product;
        }
    }


}
