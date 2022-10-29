package com.plotnikov.project.service;

import com.plotnikov.project.model.Basket;
import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.model.product.Product;
import com.plotnikov.project.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BasketServiceImpl implements IBasketService {
    private final BasketRepository basketRepository;
    private final CustomerService customerService;

    private  final ProductServiceImpl productService;

    @Autowired
    public BasketServiceImpl(BasketRepository basketRepository, CustomerService customerService, ProductServiceImpl productService) {
        this.basketRepository = basketRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public Basket createBasket() {
        return basketRepository.save(new Basket());
    }

    @Override
    public void addProductToBasket(Product product, Principal principal){
        Customer customer = customerService.getCustomerByPrincipal(principal);
        Basket basket = customer.getBasket();
        basket.getProductsInBasket().add(product);
        basketRepository.save(basket);
    }

    @Override
    public void removeFromBasket(Product product, Principal principal) {
        Customer customer = customerService.getCustomerByPrincipal(principal);
        Basket basket = customer.getBasket();
        List<Product> productsInBasket = basket.getProductsInBasket();
        productsInBasket.removeIf(product1 -> product1.equals(product));
    }

    @Override
    public void clearBasket(Principal principal) {
        Customer customer = customerService.getCustomerByPrincipal(principal);
        Basket basket = customer.getBasket();
        basket.setProductsInBasket(Collections.emptyList());
        basketRepository.save(basket);
    }

    @Override
    public List<Product> getAllProductsFromBasket(Principal principal) {
        Customer customer = customerService.getCustomerByPrincipal(principal);
        return customer.getBasket().getProductsInBasket();
    }

    @Override
    public List<Product> getProductsFromBasketByProductId(Principal principal, String productId) {
        List<Product> result = new ArrayList<>();
        Customer customer = customerService.getCustomerByPrincipal(principal);
        for(Product product : customer.getBasket().getProductsInBasket()){
            if(product.getId().equals(productId)){
                result.add(product);
            }
        }
        return result;
    }

    @Override
    public Product findById(String id, Customer customer) {
        return null;
    }

    @Override
    public List<Product> removeProductFromBasket(Principal principal, Product product) {
        List<Product> productsInBasket = customerService.getCustomerByPrincipal(principal).getBasket().getProductsInBasket();
        productsInBasket.remove(product);
        return productsInBasket;
    }
}
