package ru.artemaa.shaurma.kitchen.messaging;

import ru.artemaa.shaurma.Order;

public interface OrderReceiver {
    Order receiverOrder() throws Exception;
}
