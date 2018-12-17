package ru.artemaa.shaurma.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.artemaa.shaurma.Order;
import ru.artemaa.shaurma.kitchen.KitchenUI;

public class OrderListener {
    private final KitchenUI kitchenUI;

    public OrderListener(KitchenUI kitchenUI) {
        this.kitchenUI = kitchenUI;
    }

    @RabbitListener(queues = "shaurmacloud.order.queue")
    public void receiveOrder(Order order) {
        kitchenUI.displayOrder(order);
    }
}
