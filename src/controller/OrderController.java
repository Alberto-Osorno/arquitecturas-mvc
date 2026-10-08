package controller;

import exceptions.ValidationException;
import model.Order;
import model.OrderModel;
import view.OrderView;

public class OrderController {
    private final OrderModel model;
    private final OrderView view;

    public OrderController(OrderModel model, OrderView view){
        this.model = model;
        this.view = view;
    }

    public void registerOrder(){
        try {
            Order order = view.captureOrder();
            Order result = model.registerOrder(order);
            view.printOrder(result);
        } catch (ValidationException e){
            view.printError(e.getMessage());
        }
    }

    public void consultOrder(){
        int orderID = view.consultOrder();
        Order result = model.consultOrder(orderID);

        if (result == null) {
            view.printError("Pedido no encontrado");
            return;
        }

        view.printOrder(result);
    }
}
