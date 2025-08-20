package dao;

import model.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void createTable(Connection connection, String table_name) {
        String sql = "CREATE TABLE IF NOT EXISTS " + table_name + " (id SERIAL PRIMARY KEY, name VARCHAR(75) NOT NULL, price DECIMAL(10, 2) NOT NULL)";
       try(Statement stmt = connection.createStatement()){
           stmt.executeUpdate(sql);
           System.out.println("Table has successfully been created");
       }catch(Exception e) {
           System.out.println("Error creating table: " + e.getMessage());
       }
    }


    public void insertProducts(Connection connection, Products product) {
        String sql = "insert into products(name, price) values (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.executeUpdate();
            System.out.println("successfully inserted into table");
        } catch (Exception e) {
            System.out.println("Error inserting into table" + e.getMessage());
        }
    }

    public void insertAllProducts (Connection connection) {
        String check = "Select count(*) from products";
        try(Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(check)) {
            if(rs.next() && rs.getInt(1) > 0) {
                System.out.println("Products already exist skipping insert.");
                return;
            }
        }catch(Exception e) {
            System.out.println("Error checking products table: " + e.getMessage());
        }


        List<Products> products = new ArrayList<>();
        products.add(new Products("MacBook Pro", 1200.00));
        products.add(new Products("Smartphone", 800.00));
        products.add(new Products("Tablet", 500.00));
        products.add(new Products("Smartwatch", 250.00));
        products.add(new Products("Headphones", 150.00));
        products.add(new Products("Router", 100.00));
        products.add(new Products("Monitor", 300.00));
        products.add(new Products("Keyboard", 80.00));
        products.add(new Products("Mouse", 50.00));
        products.add(new Products("Refrigerator", 1500.00));
        products.add(new Products("Washing Machine", 700.00));
        products.add(new Products("Microwave Oven", 200.00));
        products.add(new Products("Air Conditioner", 1200.00));
        products.add(new Products("Television", 900.00));
        products.add(new Products("Vacuum Cleaner", 300.00));
        products.add(new Products("Coffee Maker", 100.00));
        products.add(new Products("Blender", 80.00));
        products.add(new Products("Toaster", 40.00));
        products.add(new Products("Electric Kettle", 30.00));
        products.add(new Products("Hair Dryer", 60.00));

        for(Products product : products) {
            insertProducts(connection, product);
        }
    }


    public List<Products> getAllProducts(Connection connection) {
        List<Products> productsList = new ArrayList<>();
        String sql = "Select id, name, price from products";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                productsList.add(product);
            }

        }catch(Exception e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
        return productsList;
    }
}
