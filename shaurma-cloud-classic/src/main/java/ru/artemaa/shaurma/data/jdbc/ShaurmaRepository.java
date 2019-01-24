package ru.artemaa.shaurma.data.jdbc;

import ru.artemaa.shaurma.entity.jpa.Shaurma;

public interface ShaurmaRepository {
    Shaurma save(Shaurma shaurma);
}
