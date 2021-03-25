package de.conciso.reactivecoffeeshop.infra;

import de.conciso.reactivecoffeeshop.model.Coffee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CoffeeRepository extends ReactiveCrudRepository<Coffee, Long> {

}
