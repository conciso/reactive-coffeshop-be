package de.conciso.reactivecoffeeshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.conciso.reactivecoffeeshop.core.CoffeeChangeProducer;
import de.conciso.reactivecoffeeshop.infra.CoffeeRepository;
import de.conciso.reactivecoffeeshop.model.Coffee;
import de.conciso.reactivecoffeeshop.websocket.CoffeeWebsocketHandler;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import reactor.core.publisher.Sinks;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public Sinks.Many<Coffee> coffeeSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public CoffeeChangeProducer produceCoffee(CoffeeRepository coffeeRepository,
                                              Sinks.Many<Coffee> coffeeSink) {
        return new CoffeeChangeProducer(coffeeRepository, coffeeSink);
    }

    @Bean
    public HandlerMapping handlerMapping(Sinks.Many<Coffee> coffeeSink,
                                         CoffeeRepository coffeeRepository,
                                         ObjectMapper objectMapper) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws/coffee", new CoffeeWebsocketHandler(coffeeSink.asFlux().publish(), coffeeRepository, objectMapper));
        int order = -1; // before annotated controllers

        return new SimpleUrlHandlerMapping(map, order);
    }
}
