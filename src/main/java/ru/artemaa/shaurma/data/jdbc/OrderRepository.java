package ru.artemaa.shaurma.data.jdbc;

import ru.artemaa.shaurma.Order;

public interface OrderRepository {
    Order save(Order order);
}
