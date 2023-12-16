package ru.otus.homework.services;

import ru.otus.homework.domain.Order;

public interface TransportService {
    Order transportSlow(Order order);

    Order transportFast(Order order);
}
