package com.plotnikov.project.service;

import com.plotnikov.project.model.product.Product;

public interface IProductRepository {

    void update(String id, Product updatedProduct);
}
