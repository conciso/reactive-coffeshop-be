package de.conciso.reactivecoffeeshop;

import de.conciso.reactivecoffeeshop.core.CoffeeChangeProducer;
import de.conciso.reactivecoffeeshop.infra.CoffeeRepository;
import de.conciso.reactivecoffeeshop.model.Coffee;
import de.conciso.reactivecoffeeshop.websocket.CoffeeWebsocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFlux
@EnableScheduling
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public Sinks.Many<Coffee> coffeeSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public CoffeeChangeProducer produceCoffee(Sinks.Many<Coffee> coffeeSink) {
        return new CoffeeChangeProducer(coffeeSink);
    }

    @Bean
    public HandlerMapping handlerMapping(Sinks.Many<Coffee> coffeeSink,
                                         CoffeeRepository coffeeRepository) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/coffee", new CoffeeWebsocketHandler(coffeeSink, coffeeRepository));
        int order = -1; // before annotated controllers

        return new SimpleUrlHandlerMapping(map, order);
    }
}
