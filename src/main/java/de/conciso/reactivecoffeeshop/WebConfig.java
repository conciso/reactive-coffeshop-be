package de.conciso.reactivecoffeeshop;

import de.conciso.reactivecoffeeshop.infra.CoffeeRepository;
import de.conciso.reactivecoffeeshop.model.Coffee;
import de.conciso.reactivecoffeeshop.model.CoffeeState;
import de.conciso.reactivecoffeeshop.websocket.CoffeeWebsocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.HandlerMapping;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public Flux<Coffee> coffeeChanges(CoffeeRepository coffeeRepository) {
        return Flux.interval(Duration.ofSeconds(10))
                .map(index -> Coffee.builder()
                        .coffeeType("Type" + index)
                        .customerName("Customer" + index)
                        .state(CoffeeState.ORDERED)
                        .build())
                .flatMap(coffeeRepository::save);
    }

    @Bean
    public HandlerMapping handlerMapping(CoffeeRepository coffeeRepository) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/coffee", new CoffeeWebsocketHandler(coffeeRepository));
        int order = -1; // before annotated controllers

        return new SimpleUrlHandlerMapping(map, order);
    }
}
