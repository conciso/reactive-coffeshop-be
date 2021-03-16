package de.conciso.reactivecoffeeshop.websocket;


import de.conciso.reactivecoffeeshop.model.Coffee;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class CoffeeWebsocketHandler implements WebSocketHandler {

    private final Flux<Coffee> coffeeUpdates;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(coffeeUpdates.map(Coffee::toString).map(session::textMessage));
    }
}
