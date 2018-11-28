package ru.artemaa.shaurma.data.jdbc;

import ru.artemaa.shaurma.Shaurma;

public interface ShaurmaRepository {
    Shaurma save(Shaurma shaurma);
}
