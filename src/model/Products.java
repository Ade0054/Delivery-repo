package model;


import java.util.LinkedList;
import java.util.List;


public class Products {

    private int id;
    private String name;
    private double price;
    private LinkedList<Products> products = new LinkedList<>();

    List<Products> productsList;

    public Products() {
    }

    public Products(String name, double price) {
        this.name = name;
        this.price = price;
      
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

   public void setProductsList(List<Products>productsList) {
        this.productsList = productsList;
   }
@Override
    public String toString() {
        return name + " -$" + price;
}

public void setProducts(LinkedList<Products> products) {
        this.products = products;
}

}


