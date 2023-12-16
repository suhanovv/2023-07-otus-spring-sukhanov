package ru.otus.homework.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Order;
import ru.otus.homework.domain.Product;
import ru.otus.homework.domain.UserBasket;

import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MarketServiceImpl implements MarketService {

    @Override
    public Order collectOrder(UserBasket basket) {
        log.info("Collecting {}", basket);
        delay();
        return new Order(basket.getId(), basket.getItems().stream()
                .map(i -> new Product(i.getName()))
                .collect(Collectors.toList()));
    }

    private static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
