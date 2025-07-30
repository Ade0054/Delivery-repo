import java.util.LinkedList;
// import java.util.List;


public class Products {

    private int id;
    private String name;
    private double price;
    private LinkedList<Products> products = new LinkedList<>();

    public Products() {
    }

    public Products(int id, String name, double price) {
        this.id = id;
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


    public LinkedList<Products> getProductsList() {
    products.add(new Products(1, "MacBook Pro", 1200.00));
    products.add(new Products(2, "Smartphone", 800.00));
    products.add(new Products(3, "Tablet", 500.00));
    products.add(new Products(4, "Smartwatch", 250.00));
    products.add(new Products(5, "Headphones", 150.00));
    products.add(new Products(6, "Router", 100.00));
    products.add(new Products(7, "Monitor", 300.00));
    products.add(new Products(8, "Keyboard", 80.00));
    products.add(new Products(9, "Mouse", 50.00));
    products.add(new Products(10, "Refrigerator", 1500.00));
    products.add(new Products(11, "Washing Machine", 700.00));
    products.add(new Products(12, "Microwave Oven", 200.00));
    products.add(new Products(13, "Air Conditioner", 1200.00));
    products.add(new Products(14, "Television", 900.00));
    products.add(new Products(15, "Vacuum Cleaner", 300.00));
    products.add(new Products(16, "Coffee Maker", 100.00));
    products.add(new Products(17, "Blender", 80.00));
    products.add(new Products(18, "Toaster", 40.00));
    products.add(new Products(19, "Electric Kettle", 30.00));
    products.add(new Products(20, "Hair Dryer", 60.00));
    return products;
}

}


