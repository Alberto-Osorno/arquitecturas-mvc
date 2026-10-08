import controller.OrderController;
import model.OrderModel;
import view.OrderView;

public class Main {
    public static void main(String[] args) {
        OrderModel model = new OrderModel();
        OrderView view = new OrderView();

        OrderController controller = new OrderController(model, view);

        controller.registerOrder();
        controller.consultOrder();
    }
}
