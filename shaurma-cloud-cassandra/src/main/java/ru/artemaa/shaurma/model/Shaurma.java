package ru.artemaa.shaurma.model;

import com.datastax.driver.core.utils.UUIDs;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table("shaurmas")
public class Shaurma {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID id = UUIDs.timeBased();

    private String name;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date createdAt = new Date();

    @Column("ingredients")
    private List<IngredientUDT> ingredients;
}
