package ru.artemaa.shaurma.data.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.artemaa.shaurma.Shaurma;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import static java.util.Arrays.asList;

@Repository
public class JdbcShaurmaRepository implements ShaurmaRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public JdbcShaurmaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Shaurma save(Shaurma shaurma) {
        long shaurmaId = saveShaurmaInfo(shaurma);
        shaurma.setId(shaurmaId);
        shaurma.getIngredients().forEach(ingredientId -> saveIngredientToShaurma(ingredientId, shaurmaId));
        return shaurma;
    }

    private long saveShaurmaInfo(Shaurma shaurma) {
        shaurma.setCreatedAt(new Date());
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Shaurma (name, createdAt) values (?, ?)",
                Types.VARCHAR,
                Types.TIMESTAMP
        );
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(
                asList(
                        shaurma.getName(),
                        new Timestamp(shaurma.getCreatedAt().getTime())
                )
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(preparedStatementCreator, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToShaurma(String ingredientId, long shaurmaId) {
        jdbc.update(
                "insert into Shaurma_Ingredients (shaurma, ingredient) values (?, ?)",
                shaurmaId,
                ingredientId
        );
    }
}
