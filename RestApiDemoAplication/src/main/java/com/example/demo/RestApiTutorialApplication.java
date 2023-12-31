package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
public class RestApiTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiTutorialApplication.class, args);
	}

}

class Coffee {
	private final String id;
	private String name;

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

	public void setName(String name) {
		this.name = name;
	}
}

@RestController
@RequestMapping("/coffees")
class RestApiDemoController {
	private List<Coffee> coffees = new ArrayList<>();

	public RestApiDemoController() {
		coffees.addAll(List.of(
				new Coffee("Café Cereza"),
				new Coffee ("Café Ganador"),
				new Coffee("Café Lareño"),
				new Coffee("Café Três Pontas")));
	}

	@GetMapping
	Iterable<Coffee> getCoffees () {
		return coffees;
	}

	@GetMapping("/{id}")
	Optional<Coffee> getCoffeeById(@PathVariable String id) {
		for (Coffee c: coffees) {
			if(c.getId().equals(id)){
				return Optional.of(c);
			}
		}

		return Optional.empty();
	}

//	coffee 객체와 다른 형태로 보내면 어찌되지???
	@PostMapping
	Coffee postCoffees(@RequestBody Coffee coffee) {
		coffees.add(coffee);
		return coffee;
	}

	@PutMapping("/{id}")
	ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
		int coffeeIndex = -1;

		for (Coffee c: coffees) {
			if(c.getId().equals(id)){
				coffeeIndex = coffees.indexOf(c);
				coffees.set(coffeeIndex, coffee);
			}
		}

		return (coffeeIndex == -1) ?
				new ResponseEntity<>(postCoffees(coffee), HttpStatus.CREATED) :
				new ResponseEntity<>(coffee, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteCoffee(@PathVariable String id) {
		Iterator<Coffee> iterator = coffees.iterator();

		while (iterator.hasNext()) {
			Coffee coffee = iterator.next();

			if (coffee.getId().equals(id)) {
				iterator.remove(); // 현재 요소를 리스트에서 제거
				return new ResponseEntity<>(coffee, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>("not_found_coffee_error", HttpStatus.NOT_FOUND);
	}
}
