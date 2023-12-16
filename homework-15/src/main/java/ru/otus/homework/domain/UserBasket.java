package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserBasket {
    private int id;

    private List<BasketItem> items;
}
