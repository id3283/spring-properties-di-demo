package com.example.demo.controllers;

import com.example.demo.daos.CategoryDao;
import com.example.demo.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class CategoryController {

    private CategoryDao dao;

    @Autowired
    public CategoryController(CategoryDao categoryDao) {
        this.dao = categoryDao;
    }

    @RequestMapping(path="/categories", method = RequestMethod.GET)
    public ArrayList<Category> getAllCategories() {
        System.out.println("Yo, I'm in getAllCategories");
        return this.dao.getCategories();
    }

    @RequestMapping(path= "/categories/{identifier}", method = RequestMethod.GET)
    public Category getCategoryByIdOne(@PathVariable int identifier) {
        return this.dao.getCategoryById(identifier);
    }

}
