package ru.otus.homework;

import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.homework.services.OrderService;

@SpringBootApplication
public class HomeworkApplication {

    public static void main(String[] args) {
        val ctx = SpringApplication.run(HomeworkApplication.class, args);
        OrderService orderService = ctx.getBean(OrderService.class);
        orderService.makeOrders();
    }


}
