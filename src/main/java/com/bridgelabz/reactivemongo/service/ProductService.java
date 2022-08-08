package com.bridgelabz.reactivemongo.service;

import com.bridgelabz.reactivemongo.dto.ProductDTO;
import com.bridgelabz.reactivemongo.repository.ProductRepository;
import com.bridgelabz.reactivemongo.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    /**
     * To get Product list
     * @return
     */
    public Flux<ProductDTO> getProducts(){
        return repository.findAll().map(AppUtil::entityToDto);
    }

    /**
     * To get product by product-id
     * @param id
     * @return
     */
    public Mono<ProductDTO> getProduct(String id){
        return repository.findById(id).map(AppUtil::entityToDto);
    }

    /**
     * To get product within a range
     * @param min
     * @param max
     * @return
     */
    public Flux<ProductDTO> getProductInRange(double min, double max){
        return repository.findByPriceBetween(Range.closed(min, max));
    }

    /**
     * To save a product
     * flatmap : one to many mapping
     * map : for single mapping
     * @param productDTOMono
     * @return
     */
    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono){
        return productDTOMono.map(AppUtil::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtil::entityToDto);
    }

    /**
     * To update a product
     * @param productDTOMono
     * @param id
     * @return
     */
    public Mono<ProductDTO> updateProduct(Mono<ProductDTO> productDTOMono, String id){
        return repository.findById(id)
                .flatMap(product -> productDTOMono.map(AppUtil::dtoToEntity).doOnNext(e -> e.setId(id)))
                .flatMap(repository::save)
                .map(AppUtil::entityToDto);

    }

    /**
     * To delete a product
     * @param id
     * @return
     */
    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);
    }
}
