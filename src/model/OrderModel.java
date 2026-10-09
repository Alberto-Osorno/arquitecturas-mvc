package model;

import exceptions.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderModel {
    private final Map<Integer, Order> orderMap = new HashMap<>();
    private final List<OrderObserver> observers = new ArrayList<>();
    private int idCounter = 1;

    public void addObserver(OrderObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Order order, String operation) {
        for (OrderObserver observer : observers) {
            observer.update(order, operation);
        }
    }

    public Order registerOrder(Order order){
        //Se realizan las validaciones básicas sobre el pedido
        validateOrder(order);

        //Tras validar el pedido se le establece in ID correcto
        int orderId = idCounter++;
        order.setId(orderId);

        //Se calculan los valores del pedido: subtotal, descuento, impuestos y total
        calculateOrder(order);

        //Se agrega el pedido al repositorio de pedidos
        orderMap.put(orderId, order);

        return order;
    }

    public Order consultOrder(int orderId){
        return orderMap.get(orderId);
    }

    private void validateOrder(Order order){
        validateCustomer(order);
        validateProducts(order);
        validateStock(order);
    }

    private void validateCustomer(Order order){
        if (order.getCustomer() == null || order.getCustomer().isBlank()){
            order.setState(OrderState.CANCELLED);
            throw new ValidationException("No se registró ningún cliente");
        }
    }
    private void validateProducts(Order order){
        if (order.getProductList().isEmpty()){
            order.setState(OrderState.CANCELLED);
            throw new ValidationException("No se registró ningún producto");
        }
    }
    private void validateStock(Order order){
        for(Product product : order.getProductList()){
            if (product.quantity() <= 0){
                order.setState(OrderState.CANCELLED);
                throw new ValidationException(
                        "La cantidad solicitada del producto es insuficiente");
            }

            if (product.quantity() > product.stock()){
                order.setState(OrderState.CANCELLED);
                throw new ValidationException(
                        "La cantidad solicitada del producto supera la existencia");
            }
        }
    }

    private void calculateOrder(Order order){
        calculateSubtotal(order);
        notifyObservers(order, "Cálculo de subtotal");
        calculateDiscount(order);
        notifyObservers(order, "Cálculo de descuento");
        calculateTaxes(order);
        notifyObservers(order, "Cálculo de impuestos");
        calculateTotal(order);
        notifyObservers(order, "Cálculo del total");
    }

    private void calculateSubtotal(Order order){
        double sum = 0;

        for (Product product : order.getProductList()){
            sum += product.price() * product.quantity();
        }

        order.setSubtotal(sum);
    }
    private void calculateDiscount(Order order){
        double discountAmount = 0.10;

        if (order.getSubtotal() >= 1000){
            order.setDiscount(order.getSubtotal() * discountAmount);
        }
    }
    private void calculateTaxes(Order order){
        double taxAmount = 0.16;
        double subtotalAfterDiscount = order.getSubtotal() - order.getDiscount();

        order.setTaxes(subtotalAfterDiscount * taxAmount);
    }
    private void calculateTotal(Order order){
        double totalAmount = order.getSubtotal() - order.getDiscount() + order.getTaxes();

        order.setTotal(totalAmount);
        order.setState(OrderState.PROCESSED);
    }
}
