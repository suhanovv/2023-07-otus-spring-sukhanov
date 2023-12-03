package ru.otus.homework.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework.domain.Order;
import ru.otus.homework.domain.UserBasket;


@MessagingGateway
public interface MarketGateway {
    @Gateway(requestChannel = "incomingBaskets", replyChannel = "orders")
    Order collect(UserBasket basket);
}
