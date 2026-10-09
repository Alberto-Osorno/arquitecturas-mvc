package controller;

import exceptions.ValidationException;
import model.Order;
import model.OrderModel;
import view.OrderView;

public class OrderController {
    private final OrderModel model;
    private OrderView view;

    public OrderController(OrderModel model, OrderView view){
        this.model = model;
        this.view = view;
        if (this.view != null) {
            this.model.addObserver(this.view);
        }
    }

    public void setView(OrderView view) {
        if (this.view != null) {
            this.model.removeObserver(this.view);
        }
        this.view = view;
        if (this.view != null) {
            this.model.addObserver(this.view);
        }
    }

    public OrderView getView() {
        return view;
    }

    public void registerOrder(){
        try {
            Order order = view.captureOrder();
            model.registerOrder(order);
        } catch (ValidationException e){
            view.printError(e.getMessage());
        }
    }

    public void registerOrder(Order order){
        try {
            model.registerOrder(order);
        } catch (ValidationException e){
            view.printError(e.getMessage());
        }
    }

    public void consultOrder(){
        int orderID = view.consultOrder();
        consultOrder(orderID);
    }

    public void consultOrder(int orderId){
        Order result = model.consultOrder(orderId);

        if (result == null) {
            view.printError("Pedido no encontrado");
            return;
        }

        view.printOrder(result);
    }

    public void registrarPedido() {
        registerOrder();
    }

    public void registrarPedido(Order order) {
        registerOrder(order);
    }

    public void consultarPedido(int id) {
        consultOrder(id);
    }
}
