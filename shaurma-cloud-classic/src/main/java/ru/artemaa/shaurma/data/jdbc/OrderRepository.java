package ru.artemaa.shaurma.data.jdbc;

import ru.artemaa.shaurma.entity.jpa.Order;

public interface OrderRepository {
    Order save(Order order);
}
