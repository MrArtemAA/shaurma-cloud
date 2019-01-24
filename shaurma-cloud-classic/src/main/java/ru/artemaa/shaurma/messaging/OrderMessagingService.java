package ru.artemaa.shaurma.messaging;

import ru.artemaa.shaurma.entity.jpa.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
