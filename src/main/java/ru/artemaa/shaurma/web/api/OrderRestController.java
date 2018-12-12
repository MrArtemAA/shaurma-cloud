package ru.artemaa.shaurma.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import ru.artemaa.shaurma.Order;
import ru.artemaa.shaurma.data.jpa.OrderRepository;

import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/orders", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class OrderRestController {
    private final OrderRepository orderRepository;

    public OrderRestController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PutMapping("/{id}")
    public Order putOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PatchMapping("/{id}")
    public Order patchOrder(@PathVariable("id") Long id, @RequestBody Order patchOrder) {
        Order order = orderRepository.findById(id).get();
        if (nonNull(patchOrder.getName())) {
            order.setName(patchOrder.getName());
        }
        // etc
        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignore) {}
    }
}
