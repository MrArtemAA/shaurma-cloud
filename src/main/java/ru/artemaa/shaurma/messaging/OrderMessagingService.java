package ru.artemaa.shaurma.messaging;

import ru.artemaa.shaurma.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
