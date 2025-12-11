package com.example.demo.controllers;

import com.example.demo.daos.ProductDao;
import com.example.demo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class ProductController {

    ProductDao dao;

    @Autowired
    public ProductController(ProductDao dao) {

        this.dao = dao;
    }

    //serialization - series
    @RequestMapping(path="/products", method=RequestMethod.GET)
    public ArrayList<Product> getAllProducts() {

        return this.dao.getAllProducts();
    }

    @RequestMapping(path="/products/{id}", method=RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        return this.dao.getProductById(id);
    }

    @RequestMapping(path="/products", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product createNewProduct(@RequestBody Product newProduct) {
        return this.dao.createNewProduct(newProduct);
    }

    @RequestMapping(path="/products/{id}", method=RequestMethod.PUT)
    public void updateProductById(@PathVariable int id, @RequestBody Product p) {
        if (id != p.getProductId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The product id in the path is different from the product id in the body.");
        }

        this.dao.updateProductById(id, p);
    }

    @RequestMapping(path="/products/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    public void deleteProductById(@PathVariable int id) {
        this.dao.deleteProductById(id);
    }

}
