package com.example.demo.daos;

import com.example.demo.models.Category;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class CategoryDao {
    BasicDataSource dataSource;

    @Autowired
    public CategoryDao(@Value("${datasource.url}") String url,
                       @Value("${datasource.username}") String userName,
                       @Value("${datasource.password}") String password) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        this.dataSource = dataSource;
    }


    public ArrayList<Category> getCategories() {
        String sql = "SELECT * FROM Categories;";

        ArrayList<Category> categories = new ArrayList<Category>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Category c = new Category(resultSet.getInt("CategoryID"), resultSet.getString("CategoryName"));
                categories.add(c);
            }

            return categories;

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }
        return null;
    }


    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM categories where CategoryID = ?;";
        Category category = new Category();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            category.setCategoryId(id);
            category.setCategoryName(resultSet.getString("CategoryName"));

            return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}