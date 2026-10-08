package view;

import model.Order;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class OrderView {
    private final Scanner scan = new Scanner(System.in);

    public Order captureOrder(){
        System.out.println();
        System.out.println("========= Registrar pedido nuevo ========");
        System.out.print("   Cliente: ");
        String customer = scan.nextLine();

        System.out.println("Productos");
        List<Product> productList = new ArrayList<Product>();
        String selection;

        do{
            System.out.print("   Nombre del producto: ");
            String name = scan.nextLine();

            double price;
            while (true){
                try{
                    System.out.print("   Precio: $");
                    price = parseDouble(scan.nextLine());
                    break;
                } catch (NumberFormatException e){
                    printError("Entrada invalida, intente de nuevo");
                }
            }

            int quantity;
            while (true){
                try{
                    System.out.print("   Cantidad: ");
                    quantity = parseInt(scan.nextLine());
                    break;
                } catch (NumberFormatException e){
                    printError("Entrada invalida, intente de nuevo");
                }
            }

            int stock;
            while (true){
                try{
                    System.out.print("   Existencia: ");
                    stock = parseInt(scan.nextLine());
                    break;
                } catch (NumberFormatException e){
                    printError("Entrada invalida, intente de nuevo");
                }
            }

            productList.add(new Product(name, price, quantity, stock));

            do {
                System.out.print("¿Agregar otro producto? (s/n): ");
                selection = scan.nextLine();
            } while (!selection.equalsIgnoreCase("s") && !selection.equalsIgnoreCase("n"));

        } while (selection.equalsIgnoreCase("s"));

        return new Order(customer, productList);
    }

    public int consultOrder(){
        System.out.println();
        System.out.println("============ Consultar pedido ===========");

        int orderID;
        while (true){
            try{
                System.out.print("   ID del pedido: ");
                orderID = parseInt(scan.nextLine());
                break;
            } catch (NumberFormatException e){
                printError("Entrada invalida, intente de nuevo");
            }
        }
        return orderID;
    }

    public void printOrder(Order order){
        System.out.println();
        System.out.println("===== Pedido #" + order.getId() + " =====");
        System.out.println("Cliente: " + order.getCustomer());
        System.out.println();
        System.out.println("Productos:");
        System.out.println("-----------------------------------------");
        System.out.println("Cantidad | Descripción | Precio | Total ");
        System.out.println("-----------------------------------------");

        for(Product product : order.getProductList()){
            double productTotalPrice = product.quantity() * product.price();
            System.out.println(product.quantity() + " | " + product.name() + " | $" + product.price() + " | $" + productTotalPrice);
        }

        System.out.println("-----------------------------------------");
        System.out.println("Subtotal: $" + order.getSubtotal());
        System.out.println("Descuento: $" + order.getDiscount());
        System.out.println("Impuestos: $" + order.getTaxes());
        System.out.println("Total: $" + order.getTotal());
        System.out.println("Estado: " + order.getState());
    }

    public void printError(String error){
        System.out.println("\n[ERROR] " + error);
    }
}
