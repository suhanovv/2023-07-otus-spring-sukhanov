package ru.otus.homework.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.BasketItem;
import ru.otus.homework.domain.UserBasket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MarketGateway market;

    @Override
    public void makeOrders() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            pool.execute(() -> {
                UserBasket basket = generateBasket(num);
                log.info("{}, Create basket: {}", num, basket);
                val order = market.collect(basket);
                log.info("{}, Ready order: {}", num, order);
            });
            delay();
        }
    }

    private static BasketItem generateBasketItem() {
        val productNames = List.of(
                "Молоко", "Хлеб", "Колбаса", "Творог",
                "Ножи", "Гречка", "Рис", "Салфетки", "Масло", "Байкал");
        return new BasketItem(productNames.get(RandomUtils.nextInt(0, productNames.size())));
    }

    private static UserBasket generateBasket(int id) {

        List<BasketItem> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(3, 10); ++i) {
            items.add(generateBasketItem());
        }
        return new UserBasket(id, items);
    }

    private static void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
