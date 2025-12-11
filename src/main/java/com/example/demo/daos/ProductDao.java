package com.example.demo.daos;

import com.example.demo.models.Product;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
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

    public ArrayList<Product> getAllProducts() {
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

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);){

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

    public Product createNewProduct(Product p) {
        String sql = " INSERT INTO Products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?);" ;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, p.getProductName());
            preparedStatement.setInt(2, p.getCategoryId());
            preparedStatement.setFloat(3, p.getUnitPrice());

            int rowsInserted = preparedStatement.executeUpdate();

            if(rowsInserted != 1) {
                System.err.println("Something crazy is happening.. number of rows inserted ain't 1 🤪");
            }

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            resultSet.next();
            int productId = resultSet.getInt(1);
            resultSet.close();

            return getProductById(productId);

        } catch (SQLException e) {
            System.err.println("Uh, oh.... " + e);;
        }

        return null;
    }

    public void updateProductById(int pid, Product p) {
        String sql = """
            UPDATE Products
            SET ProductName = ?, CategoryID = ?, UnitPrice = ?
            WHERE ProductID = ?;
            """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, p.getProductName());
            preparedStatement.setInt(2, p.getCategoryId());
            preparedStatement.setFloat(3, p.getUnitPrice());
            preparedStatement.setInt(4, p.getProductId());

            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
            if(rowsUpdated != 1) {
                System.err.println("Houston, we've got a little problem here updating a product: " + pid);
                throw new RuntimeException("Number of rows updated didn't equal 1");
            }

        } catch (SQLException e) {
            System.err.println("Explosion 💥: " + e);
        }

    }
}
