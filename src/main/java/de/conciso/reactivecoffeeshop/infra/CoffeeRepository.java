package de.conciso.reactivecoffeeshop.infra;

import de.conciso.reactivecoffeeshop.model.Coffee;
import de.conciso.reactivecoffeeshop.model.CoffeeState;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CoffeeRepository extends ReactiveCrudRepository<Coffee, Long> {

    Mono<Coffee> findFirstByCoffeeTypeAndState(String coffeeType, CoffeeState coffeeState);

}
