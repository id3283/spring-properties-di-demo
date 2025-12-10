package com.example.demo.controllers;

import com.example.demo.daos.ProductDao;
import com.example.demo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CategoryController {

    ProductDao dao;

    @Autowired
    public CategoryController(ProductDao dao) {
        this.dao = dao;
    }


    @RequestMapping(value = "/categories", method=RequestMethod.GET)

    public ArrayList<Product> getAllCategories() {
        return dao.getProducts();
    }

    @RequestMapping(value = "/categories/{id}", method=RequestMethod.GET)

    public Product getProductById(@PathVariable int id) {
        return dao.getProductById(id);
    }


}
