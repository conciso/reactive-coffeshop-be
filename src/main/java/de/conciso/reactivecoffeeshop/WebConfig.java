package de.conciso.reactivecoffeeshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.conciso.reactivecoffeeshop.websocket.CoffeeMessage;
import de.conciso.reactivecoffeeshop.websocket.CoffeeWebsocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

import static reactor.util.concurrent.Queues.SMALL_BUFFER_SIZE;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public Sinks.Many<CoffeeMessage> coffeeSink() {
        return Sinks.many().multicast().onBackpressureBuffer(SMALL_BUFFER_SIZE, false);
    }

    @Bean
    public HandlerMapping handlerMapping(ObjectMapper objectMapper) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws/coffee", new CoffeeWebsocketHandler(objectMapper));
        int order = -1; // before annotated controllers

        return new SimpleUrlHandlerMapping(map, order);
    }
}
