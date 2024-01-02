package com.example.H2DBDemoAplication;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
public class H2DbDemoAplicationApplication {
	public static void main(String[] args) {
		SpringApplication.run(H2DbDemoAplicationApplication.class, args);
	}
}

@Component
class DataLoader {
	private final CoffeeRepository coffeeRepository;

	public DataLoader(CoffeeRepository coffeeRepository) {
		this.coffeeRepository = coffeeRepository;
	}

	@PostConstruct
	private void loadData() {
		coffeeRepository.saveAll(List.of(
				new Coffee("Café Cereza"),
				new Coffee ("Café Ganador"),
				new Coffee("Café Lareño"),
				new Coffee("Café Três Pontas")));
	}
}

@Entity
class Coffee {
	@Id
	private String id;
	private String name;

	public Coffee() {
	}

	public Coffee(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Coffee(String name) {
		this(UUID.randomUUID().toString(), name);
	}

	public String getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}

interface CoffeeRepository extends CrudRepository<Coffee, String> {}

@RestController
@RequestMapping("/coffees")
class RestApiDemoController {
	private final CoffeeRepository coffeeRepository;

	public RestApiDemoController(CoffeeRepository coffeeRepository) {
		this.coffeeRepository = coffeeRepository;
	}


	@GetMapping
	Iterable<Coffee> getCoffees () {
		return coffeeRepository.findAll();
	}

	@GetMapping("/{id}")
	Optional<Coffee> getCoffeeById(@PathVariable String id) {
		return coffeeRepository.findById(id);
	}

	//	coffee 객체와 다른 형태로 보내면 어찌되지???
	@PostMapping
	Coffee postCoffees(@RequestBody Coffee coffee) {
		return coffeeRepository.save(coffee);
	}

	@PutMapping("/{id}")
	ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
		return (coffeeRepository.existsById(id)) ?
				new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK):
				new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED);

	}

	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteCoffee(@PathVariable String id) {
		if(coffeeRepository.existsById(id)) {
			coffeeRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>("not_found_coffee_error", HttpStatus.NOT_FOUND);
	}
}
