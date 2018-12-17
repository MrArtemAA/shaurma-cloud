package ru.artemaa.shaurma.kitchen;

import org.springframework.stereotype.Component;
import ru.artemaa.shaurma.Order;

@Component
public class KitchenUI {
    public void displayOrder(Order order) {
        System.out.println(order.toString());
    }
}
