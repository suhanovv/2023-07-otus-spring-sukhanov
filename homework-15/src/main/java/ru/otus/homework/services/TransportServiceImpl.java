package ru.otus.homework.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Order;

import java.util.Date;

@Slf4j
@Service
public class TransportServiceImpl implements TransportService {
    @Override
    public Order transportSlow(Order order) {
        log.info("Transporting slow starts at {} order {}", new Date(), order);
        delay(3000);
        return order;
    }

    @Override
    public Order transportFast(Order order) {
        log.info("Transporting fast starts at {} order {}", new Date(), order);
        delay(1000);
        return order;
    }

    private static void delay(Integer d) {
        try {
            Thread.sleep(d);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
