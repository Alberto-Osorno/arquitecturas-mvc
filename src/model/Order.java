package model;

import java.util.List;

public class Order {
    private int id;
    private final String customer;
    private final List <Product> productList;
    private double subtotal;
    private double discount;
    private double taxes;
    private double total;
    private OrderState state;

    public Order(String customer, List<Product> productList){
        this.id = 0;
        this.customer = customer;
        this.productList = productList;
        this.subtotal = 0;
        this.discount = 0;
        this.taxes = 0;
        this.total = 0;
        this.state = OrderState.PENDING;
    }

    //Getters
    public int getId() { return id;}
    public String getCustomer() { return customer;}
    public List<Product> getProductList() { return productList;}
    public double getSubtotal() { return subtotal;}
    public double getDiscount() { return discount;}
    public double getTaxes() { return taxes;}
    public double getTotal() { return total;}
    public OrderState getState() { return state;}

    //Setters
    public void setId(int id){ this.id = id;}
    public void setSubtotal(double subtotal) { this.subtotal = subtotal;}
    public void setDiscount(double discount) { this.discount = discount;}
    public void setTaxes(double taxes) { this.taxes = taxes;}
    public void setTotal(double total) { this.total = total;}
    public void setState(OrderState state) { this.state = state;}
}
