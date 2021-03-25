package de.conciso.reactivecoffeeshop.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class CoffeeWebsocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper;

    public CoffeeWebsocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return Mono.empty();
    }

    private Mono<String> writeJson(CoffeeMessage coffee) {
        return Mono.fromCallable(() -> writeValueAsString(coffee))
                .onErrorMap(WrappedJsonProcessingException.class, RuntimeException::getCause)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private String writeValueAsString(CoffeeMessage coffee) {
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
