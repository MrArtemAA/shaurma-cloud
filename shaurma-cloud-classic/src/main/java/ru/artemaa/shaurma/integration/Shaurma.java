package ru.artemaa.shaurma.integration;

import lombok.Data;

import java.util.List;

@Data
public class Shaurma {
    private final String name;
    private List<String> ingredients;
}
