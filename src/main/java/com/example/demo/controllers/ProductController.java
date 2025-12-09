package com.example.demo.controllers;

import com.example.demo.daos.ProductDao;
import com.example.demo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        return this.dao.getProducts();
    }

    @RequestMapping(path="/products/{id}", method=RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        return this.dao.getProductById(id);
    }

}
