package ru.artemaa.shaurma.data;

import ru.artemaa.shaurma.Order;

public interface OrderRepository {
    Order save(Order order);
}
