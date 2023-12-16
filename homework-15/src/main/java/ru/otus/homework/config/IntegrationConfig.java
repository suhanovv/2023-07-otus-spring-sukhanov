package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.homework.domain.Order;
import ru.otus.homework.services.MarketService;
import ru.otus.homework.services.TransportService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> incomingBaskets() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> orders() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(1000).maxMessagesPerPoll(1);
    }

    @Bean
    public IntegrationFlow marketFlow(MarketService marketService, TransportService transportService) {
        return IntegrationFlow.from(incomingBaskets())
                .handle(marketService, "collectOrder")
                .<Order, Boolean>route(
                        i -> i.getProducts().size() > 3,
                        mapping -> mapping
                                .subFlowMapping(true, sf -> sf
                                        .handle(transportService, "transportSlow"))
                                .subFlowMapping(false, sf ->  sf
                                        .handle(transportService, "transportFast"))
                ).channel(orders())
                .get();
    }
}


