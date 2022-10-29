package com.plotnikov.project.controller;

import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.model.product.Product;
import com.plotnikov.project.service.BasketServiceImpl;
import com.plotnikov.project.service.CustomerService;
import com.plotnikov.project.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private BasketServiceImpl basketService;
    private ProductServiceImpl productService;
    private CustomerService customerService;

    @Autowired
    public BasketController(BasketServiceImpl basketService, ProductServiceImpl productService, CustomerService customerService) {
        this.basketService = basketService;
        this.productService = productService;
        this.customerService = customerService;
    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
    public ModelAndView getBasket(ModelAndView modelAndView,
                                  Principal principal
    ) {
        Customer customer = customerService.getCustomerByPrincipal(principal);

        Map<Product, Integer> productIntegerMap = new HashMap<>();
        for (Product product : customer.getBasket().getProductsInBasket()) {
            if (productIntegerMap.containsKey(product)) {
                productIntegerMap.put(product, productIntegerMap.get(product) + 1);
            } else {
                productIntegerMap.put(product, 1);
            }
        }
        modelAndView.addObject("productsMap", productIntegerMap.entrySet());
        modelAndView.setViewName("basket");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/delete/{productId}")
    public ModelAndView deleteProductFromBasket(ModelAndView modelAndView,
                                                Principal principal,
                                                @PathVariable String productId

//                                           @RequestBody String body
    ) {
        Product product = productService.findById(productId).get();
        Customer customer = customerService.getCustomerByPrincipal(principal);
        boolean success = customer.getBasket().getProductsInBasket().remove(product);
        if(success){
            modelAndView.addObject("message", "Product has been delete");
            customer = customerService.save(customer);
        }else{
            modelAndView.addObject("error", "No product");
        }
        return getBasket(modelAndView,principal);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/addProduct")
    public ModelAndView addProductToBasket(ModelAndView modelAndView,
                                           Principal principal,
                                           @RequestBody String body
    ) {
        Product product = productService.findById(body.substring(10)).get();
        basketService.addProductToBasket(product, principal);
        modelAndView.setViewName("redirect:" + "/basket");
        return modelAndView;
    }


//    @PostMapping("/basket/{basketId}")
//    public  ModelAndView clearBasket(ModelAndView modelAndView, @PathVariable String basketId){
//        basketService.clearBasket();
//        modelAndView.setViewName("redirect:" + "/products");
//        return modelAndView;
//    }
//
//    @PreAuthorize("hasAuthority('USER')")
//    @PostMapping("/products")
//    public ModelAndView addProductToBasket(@ModelAttribute Product product,
//                                           ModelAndView modelAndView,
//                                           @PathVariable String customerId){
//        Basket basket = customerService.findById(customerId).getBasket();
//        basketService.addProductToBasket(product);
//        modelAndView.setViewName("allProducts");
//        return modelAndView;
//    }

}
