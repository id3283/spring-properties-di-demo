package com.example.demo.daos;

import com.example.demo.models.Product;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// 🫘
@Component
public class ProductDao {
    private BasicDataSource dataSource;

    @Autowired
    public ProductDao(@Value("${datasource.url}") String url,
                      @Value("${datasource.username}") String userName,
                      @Value("${DB_PASSWORD}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        this.dataSource = dataSource;
    }

    public ArrayList<Product> getProducts() {
        String sql = "SELECT * FROM Products;";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Product> result = new ArrayList<Product>();

            while(resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                String name = resultSet.getString("ProductName");
                int categoryId = resultSet.getInt("CategoryID");
                float unitPrice = resultSet.getFloat("UnitPrice");

                Product p = new Product();
                p.setProductId(id);
                p.setProductName(name);
                p.setCategoryId(categoryId);
                p.setUnitPrice(unitPrice);
                result.add(p);
            }
            return result;
        } catch (SQLException e) {
            System.err.println("Houston, we have a database problem: " + e);
        }
        return null;
    }

    public Product getProductById(int id) {
        String sql = """
                SELECT *
                FROM Products
                WHERE ProductID = ?;
                """;

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int productID = resultSet.getInt("ProductID");
            String productName = resultSet.getString("ProductName");
            int categoryId = resultSet.getInt("CategoryID");
            float unitPrice = resultSet.getFloat("UnitPrice");

            return new Product(productID, productName, categoryId, unitPrice );

        } catch (SQLException e) {
            System.err.println("Houston, we have a database problem: " + e);
        }
        return null;
    }


}
