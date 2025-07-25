package com.Akaei.Store.BountyMarket.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.Akaei.Store.BountyMarket.Models.Product;
import com.Akaei.Store.BountyMarket.Services.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*\
 * APIController.java
 * This controller handles API requests for the Bounty Market application, containing business logic and modification.
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class APIController {
    private final ProductService service;

    @Autowired
    public APIController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(){
        return "Welcome to Bounty Market API!";
    }

    @GetMapping("/products")
    public Mono<ResponseEntity<List<Product>>> getAllProducts(){
        Flux<Product> products = service.getAllProducts();
        return products.collectList().map(list -> {
            if(list.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(list);
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(Product product){
        return service.createProduct(product);
    }

    @DeleteMapping("/products/{id}")
    public Mono<ResponseEntity<Void>> deleteProductByID(@PathVariable Integer id){
        return service.deleteProductById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }


}
/*
To implement caching with Redis in your Spring Boot project, follow these steps:

1. Add dependencies to your `pom.xml`:
    - spring-boot-starter-data-redis-reactive
    - spring-boot-starter-cache

2. Configure Redis in `application.properties`:
    ```
    spring.cache.type=redis
    spring.redis.host=localhost
    spring.redis.port=6379
    ```

3. Enable caching in your main application class:
    ```
    @SpringBootApplication
    @EnableCaching
    public class BountyMarketApplication { ... }
    ```

4. Annotate your repository or service methods with `@Cacheable`:
    ```

    @Cacheable("products")
    public Flux<Product> getAllProducts() {
         return repository.findAll();
    }
    ```

Note: For reactive repositories, you may need to use a custom cache manager or a library that supports reactive caching, as Spring's default cache abstraction is not fully reactive.
*/