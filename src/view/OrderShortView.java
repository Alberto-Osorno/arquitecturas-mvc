package view;

import model.Order;

public class OrderShortView extends OrderView {

    @Override
    public void printOrder(Order order) {

        System.out.println();
        System.out.println("===== Resumen del Pedido =====");
        System.out.println("Pedido: " + order.getId());
        System.out.println("Total: $" + order.getTotal());
        System.out.println("Estado: " + order.getState());
    }
}