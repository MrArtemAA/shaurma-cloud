package ru.artemaa.shaurma.kitchen.messaging.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.artemaa.shaurma.Order;
import ru.artemaa.shaurma.kitchen.KitchenUI;

@Component
public class OrderListener {
    private final KitchenUI kitchenUI;

    public OrderListener(KitchenUI kitchenUI) {
        this.kitchenUI = kitchenUI;
    }

    @JmsListener(destination = "shaurmacloud.order.queue")
    public void receiveOrder(Order order) {
        kitchenUI.displayOrder(order);
    }
}
