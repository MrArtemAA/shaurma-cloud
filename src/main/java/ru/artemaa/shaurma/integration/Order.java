package ru.artemaa.shaurma.integration;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private final String email;
    private List<Shaurma> shaurmas = new ArrayList<>();

    public void addShaurma(Shaurma shaurma) {
        shaurmas.add(shaurma);
    }
}
