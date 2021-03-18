package de.conciso.reactivecoffeeshop.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.conciso.reactivecoffeeshop.infra.CoffeeRepository;
import de.conciso.reactivecoffeeshop.model.Coffee;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
public class CoffeeWebsocketHandler implements WebSocketHandler {

    private final ConnectableFlux<Coffee> coffeeSink;

    private final CoffeeRepository coffeeRepository;

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(coffeeRepository.findAll()
                .concatWith(coffeeSink.autoConnect())
                .flatMap(this::writeJson)
                .map(session::textMessage));
    }

    private Mono<String> writeJson(Coffee coffee) {
        return Mono.fromCallable(() -> writeValueAsString(coffee))
                .onErrorResume(WrappedJsonProcessingException.class, exception -> Mono.error(exception.getCause()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private String writeValueAsString(Coffee coffee) {
        try {
            return objectMapper.writeValueAsString(coffee);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new WrappedJsonProcessingException(jsonProcessingException);
        }
    }

    private static class WrappedJsonProcessingException extends RuntimeException {
        private WrappedJsonProcessingException(JsonProcessingException cause) {
            super(cause);
        }
    }
}
