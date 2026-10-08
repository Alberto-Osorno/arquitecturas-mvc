package model;

public class Product {
    private final String name;
    private final double price;
    private final int quantity;
    private final int stock;

    public Product(String name, double price, int quantity, int stock){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.stock = stock;
    }

    public String getName() {return name;}
    public double getPrice() { return price;}
    public int getQuantity() { return quantity;}
    public int getStock() { return stock;}
}
