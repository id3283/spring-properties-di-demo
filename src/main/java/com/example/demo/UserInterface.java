package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserInterface {

    ProductDao productDao;

    @Autowired
    public UserInterface(ProductDao productDao) {
        this.productDao = productDao;
        printAllProductName();
    }

    public void printAllProductName() {
        ArrayList<Product> allProducts = this.productDao.getProducts();

        for(Product p: allProducts) {
            System.out.println(p.getProductName());
        }
    }
}
