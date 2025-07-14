package com.Akaei.Store.BountyMarket.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.Akaei.Store.BountyMarket.Models.Product;
import com.Akaei.Store.BountyMarket.repo.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Flux<Product> getAllProducts(){
        return repository.findAll();
    }

    @Cacheable(value = "products", key = "#name") //can't cache Flux apparently so not sure if this does any good
    public Flux<Product> getProductsByName(String username){
        return repository.findAll()
                .filter(product -> product.getUsername().equals(username));
    }

    public Mono<Product> createProduct(Product product){
        /*
         * Mono holds 0 or 1 item and used for asynchronous operations. 
         */
        return repository.save(product);
    }

    public Mono<Product> updateProduct(Integer uid, Product productDetails){
        //flatmap applies a function that can return multiple elements and flattens the result into a single stream, while map applies the function to each element. 
        return repository.findById(uid) //repository.findById(uid) Returns a Mono<Product> — an async container that emits the product with the given ID (or is empty if not found).  Important: This map returns Mono<Product> inside the Mono, making the return type Mono<Mono<Product>>.
            .flatMap(product -> {
                        product.setUsername(productDetails.getUsername());
                        product.setLink(productDetails.getLink());
                        product.setPrice(productDetails.getPrice());
                        return repository.save(product); // returns Mono<Product>
                    });
        }
    }
