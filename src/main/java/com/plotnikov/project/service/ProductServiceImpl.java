package com.plotnikov.project.service;

import com.plotnikov.project.model.product.Product;
import com.plotnikov.project.model.product.ProductTechnology;
import com.plotnikov.project.model.product.ProductType;
import com.plotnikov.project.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements IProductRepository{
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAll(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public List<Product> saveAll(Iterable<Product> products){
        List<Product> copeList = new ArrayList<>();
        for(Product p : (List<Product>) productRepository.saveAll(products)){
            copeList.add(p);
        }
        return copeList;
    }

    public Optional<Product> findById(String id){
        return productRepository.findById(id);
    }

    public List<Product> findByName(String name){
        return productRepository.findProductByNameLike(name);
    }

    public void delete(String id){
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }else {
            //todo Допиши тут
            log.warn("");
        }
    }

    public Product create(Product product){
        final Product createdProduct = productCreator(
                product.getName(),product.getPrice(),product.getCount(),
                product.getDescription(), product.getProductType(), product.getProductTechnology(),product.getImgUrl());
        productRepository.save(product);
        return product;
    }

    public List<Product> searchByName(String name){
        return productRepository.findProductByNameLike(name);
    }

    public Page<Product> searchProducts(String keyWord, Pageable pageable){
        Page<Product> all =  getAll(pageable);
        return productRepository.findAllByNameLikeOrDescriptionLike(keyWord.toLowerCase(), keyWord.toLowerCase(), pageable);
    }

    @Override
    public void update(String id, Product updatedProduct) {
        Product product = productRepository.findById(id).get();
        if (product != null){
            product.setName(updatedProduct.getName());
            product.setCount(updatedProduct.getCount());
            product.setDescription(updatedProduct.getDescription());
            product.setProductType(updatedProduct.getProductType());
            product.setProductTechnology(updatedProduct.getProductTechnology());
            product.setImgUrl(updatedProduct.getImgUrl());
            productRepository.save(product);
        }else{
            log.warn("Product with id " + id + "not found!");
        }

    }

    private Product productCreator(String name, int price, int count, String description, ProductType productType, ProductTechnology productTechnology, String imgUrl){
        final Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCount(count);
        product.setDescription(description);
        product.setProductType(productType);
        product.setProductTechnology(productTechnology);
        product.setImgUrl(imgUrl);
        return product;
    }

    public Page<Product> getProductsByProductType(ProductType productType, Pageable pageable){
        return productRepository.findProductByProductType(productType, pageable);
    }
    public Page<Product> filterByTypeAndTechnology(ProductType productType, ProductTechnology productTechnology,Pageable pageable){
        boolean filterByProductType = !productType.equals(ProductType.All);
        boolean filterByProductTechnology = !productTechnology.equals(ProductTechnology.ALL);
        Page<Product> foundProduct = Page.empty();
        if (!filterByProductType && !filterByProductTechnology){
            foundProduct = productRepository.findAll(pageable);
        }else{
            if (filterByProductType && filterByProductTechnology){
                foundProduct = productRepository.findProductByProductTypeAndProductTechnology(productType, productTechnology, pageable);
            }else {
                if(filterByProductType){
                    foundProduct =productRepository.findProductByProductType(productType, pageable);
                }
                if(filterByProductTechnology){
                    foundProduct = productRepository.findProductByProductTechnology(productTechnology, pageable);
                }
            }
        }
        return foundProduct;
    }
}
