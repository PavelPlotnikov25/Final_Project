package com.plotnikov.project.service;

import com.plotnikov.project.model.Basket;
import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.model.product.Product;

import java.security.Principal;
import java.util.List;

public interface IBasketService {

    Basket createBasket();

    void addProductToBasket(Product product, Principal principal);

    void removeFromBasket(Product product, Principal principal);

    void clearBasket(Principal principal);

    List<Product> getAllProductsFromBasket(Principal principal);

    List<Product> getProductsFromBasketByProductId(Principal principal, String productId);

    Product findById(String id, Customer customer);

    List<Product> removeProductFromBasket(Principal principal, Product product);

}
