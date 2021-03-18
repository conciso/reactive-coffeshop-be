package de.conciso.reactivecoffeeshop.websocket;

import de.conciso.reactivecoffeeshop.infra.CoffeeRepository;
import de.conciso.reactivecoffeeshop.model.Coffee;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@AllArgsConstructor
public class CoffeeWebsocketHandler implements WebSocketHandler {

    private final Sinks.Many<Coffee> coffeeSink;

    private final CoffeeRepository coffeeRepository;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(coffeeRepository.findAll()
                .concatWith(coffeeSink.asFlux())
                .map(Coffee::toString).map(session::textMessage));
    }
}
