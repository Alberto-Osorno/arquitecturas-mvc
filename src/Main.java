import controller.OrderController;
import model.Order;
import model.OrderModel;
import model.Product;
import view.OrderShortView;
import view.OrderView;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ejecutarDemostracionEvidencias();
    }

    private static void ejecutarDemostracionEvidencias() {
        OrderModel model = new OrderModel();
        OrderView view = new OrderView();

        // El Controlador vincula el Modelo y la Vista (y suscribe la Vista como Observer)
        OrderController controller = new OrderController(model, view);


        System.out.println("\n>>> EVIDENCIA 1: Registrar pedido válido con descuento (Subtotal >= $1,000) <<<");
        System.out.println("--------------------------------------------------------------------------------");
        Order pedidoConDescuento = new Order("Carlos Gómez", List.of(
                new Product("Laptop Gamer", 1200.0, 1, 5)
        ));
        controller.registerOrder(pedidoConDescuento);


        System.out.println("\n\n>>> EVIDENCIA 2: Registrar pedido válido sin descuento (Subtotal < $1,000) <<<");
        System.out.println("--------------------------------------------------------------------------------");
        Order pedidoSinDescuento = new Order("Ana Martínez", List.of(
                new Product("Mouse Óptico", 250.0, 2, 10)
        ));
        controller.registerOrder(pedidoSinDescuento);


        System.out.println("\n\n>>> EVIDENCIA 3: Intentar registrar pedidos inválidos <<<");
        System.out.println("--------------------------------------------------------------------------------");

        System.out.println("Caso: Cliente vacío");
        Order pedidoSinCliente = new Order("", List.of(
                new Product("Teclado", 300.0, 1, 10)
        ));
        controller.registerOrder(pedidoSinCliente);

        System.out.println("\nCaso: Lista de productos vacía");
        Order pedidoSinProductos = new Order("Pedro Morales", List.of());
        controller.registerOrder(pedidoSinProductos);

        System.out.println("\nCaso: Cantidad de producto insuficiente (<= 0)");
        Order pedidoCantidadCero = new Order("Pedro Morales", List.of(
                new Product("Memoria USB", 150.0, 0, 20)
        ));
        controller.registerOrder(pedidoCantidadCero);

        System.out.println("\nCaso: Cantidad de producto supera la existencia");
        Order pedidoSuperaStock = new Order("Pedro Morales", List.of(
                new Product("Monitor 4K", 4500.0, 10, 3)
        ));
        controller.registerOrder(pedidoSuperaStock);


        System.out.println("\n\n>>> EVIDENCIA 4: Consultar un pedido existente (ID: 1) <<<");
        System.out.println("--------------------------------------------------------------------------------");
        controller.consultOrder(1);


        System.out.println("\n\n>>> EVIDENCIA 5: Consultar un pedido inexistente (ID: 999) <<<");
        System.out.println("--------------------------------------------------------------------------------");
        controller.consultOrder(999);


        System.out.println("\n\n>>> EVIDENCIA 6: Ejecutar el mismo Modelo con la Vista normal y la Vista resumida <<<");
        System.out.println("--------------------------------------------------------------------------------");
        OrderShortView shortView = new OrderShortView();
        controller.setView(shortView);

        System.out.println("\nRegistrando un nuevo pedido con OrderShortView como observador:");
        Order pedidoResumido = new Order("Sofía Ramirez", List.of(
                new Product("Impresora Láser", 1500.0, 1, 4),
                new Product("Tóner Negro", 350.0, 2, 8)
        ));
        controller.registerOrder(pedidoResumido);

        System.out.println("\nConsultando pedido existente (ID: 1) mediante OrderShortView:");
        controller.consultOrder(1);
    }

    private static void ejecutarModoInteractivo() {
        OrderModel model = new OrderModel();
        OrderView view = new OrderView();
        OrderController controller = new OrderController(model, view);

        controller.registerOrder();
        controller.consultOrder();
    }
}
