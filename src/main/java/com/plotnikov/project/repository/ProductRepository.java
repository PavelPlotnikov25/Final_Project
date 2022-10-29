package com.plotnikov.project.repository;

import com.plotnikov.project.model.product.Product;
import com.plotnikov.project.model.product.ProductTechnology;
import com.plotnikov.project.model.product.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findProductByProductType(ProductType productType,Pageable pageable);
    Page<Product> findProductByProductTechnology(ProductTechnology productTechnology,Pageable pageable);

    List<Product> findProductByProductTypeOrderByPrice(ProductType productType);

    Page<Product> findProductByProductTypeAndProductTechnology(ProductType productType, ProductTechnology productTechnology, Pageable pageable);

    List<Product> findProductByNameLike(String name);

    @Query("SELECT m FROM Product m WHERE lower(m.name) LIKE  %:name% OR lower(m.description) LIKE %:description% ")
    Page<Product> findAllByNameLikeOrDescriptionLike(@Param("name") String name,@Param("description") String description, Pageable pageable);
}
