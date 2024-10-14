package com.muflikhan.backend.services;

import com.muflikhan.backend.dtos.CreateOrUpdateProductRequest;
import com.muflikhan.backend.entities.Product;
import com.muflikhan.backend.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public Product create(CreateOrUpdateProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        return  productRepository.save(product);
    }

    @Transactional
    public Product update(Long id,CreateOrUpdateProductRequest request){

        Product product = this.getProduct(id);

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return  productRepository.save(product);

    }

    @Transactional
    public void delete(Long id){
        productRepository.delete(this.getProduct(id));
    }


    private Product getProduct(Long id){
       return productRepository.findById(id).orElseThrow(()-> new  EntityNotFoundException("Product not found with id: " + id));
    }



}
