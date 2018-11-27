package ru.artemaa.shaurma.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.artemaa.shaurma.Order;
import ru.artemaa.shaurma.Shaurma;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderShaurmaInserter;
    private ObjectMapper objectMapper;

    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Shaurma_Order")
                .usingGeneratedKeyColumns("id");

        this.orderShaurmaInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Shaurma_Order_Shaurmas");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);

        order.getShaurmas().forEach(shaurma -> saveShaurmaToOrder(shaurma, orderId));

        return order;
    }

    private long saveOrderDetails(Order order) {
        //noinspection unchecked
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placesAt", order.getPlacedAt());

        return orderInserter
                .executeAndReturnKey(values)
                .longValue();
    }

    private void saveShaurmaToOrder(Shaurma shaurma, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("shaurmaOrder", orderId);
        values.put("shaurma", shaurma.getId());
        orderShaurmaInserter.execute(values);
    }
}
