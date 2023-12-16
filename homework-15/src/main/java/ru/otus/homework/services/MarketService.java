package ru.otus.homework.services;

import ru.otus.homework.domain.Order;
import ru.otus.homework.domain.UserBasket;

public interface MarketService {
    Order collectOrder(UserBasket basket);
}
