package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private int basketId;

    private List<Product> products;

}

