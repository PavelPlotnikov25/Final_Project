package com.plotnikov.project.controller;

import com.plotnikov.project.model.product.Product;
import com.plotnikov.project.model.product.ProductTechnology;
import com.plotnikov.project.model.product.ProductType;
import com.plotnikov.project.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceImpl service;

    @Autowired
    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView getArticle(ModelAndView modelAndView,
                                   @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        addAllProducts(modelAndView, PageRequest.of(pageable.getPageNumber(), 10));
        modelAndView.setViewName("allProducts");
//        modelAndView.addObject("error", "ебаная ошибка");
        return modelAndView;
    }

    @GetMapping("/{productId}")
    public ModelAndView getInfoAboutProduct(ModelAndView modelAndView,
                                            @PathVariable String productId){
        modelAndView.addObject("productInfo", service.findById(productId).get());
        modelAndView.setViewName("productInfo");
        return modelAndView;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
    public ModelAndView getFormArticle(ModelAndView modelAndView) {
        final Product product = new Product();
        product.setPrice(500);
        List<ProductType> productTypeValues = new ArrayList<>(List.of(ProductType.values()));
        List<ProductTechnology> productTechnologies = new ArrayList<>(List.of(ProductTechnology.values()));
        productTechnologies.remove(ProductTechnology.ALL);
        productTypeValues.remove(ProductType.All);
        modelAndView.addObject("product", product);
        modelAndView.addObject("productTypes",productTypeValues );
        modelAndView.addObject("productTechnologies",productTechnologies );

        modelAndView.setViewName("createProduct");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update/{productId}")
    public ModelAndView getUpdateProduct(
            @PathVariable String productId,
            ModelAndView modelAndView,
            HttpServletRequest httpServletRequest) {
        Product product = service.findById(productId).get();
//        String path = httpServletRequest.getHeader("referer").split("8080")[1];
        modelAndView.addObject("product", product);
        modelAndView.addObject("product", product);
        modelAndView.addObject("product", product);
        modelAndView.setViewName("updateProduct");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ModelAndView createOne(@ModelAttribute Product product, ModelAndView modelAndView) {
        service.create(product);
        modelAndView.setViewName("redirect:" + "/products");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public ModelAndView updateOne(@ModelAttribute Product product, ModelAndView modelAndView) {
        service.update(product.getId(), product);
        modelAndView.setViewName("redirect:" + "/products");
        return modelAndView;
    }


    @GetMapping("/search")
    public ModelAndView searchProducts(ModelAndView modelAndView,
                                       @RequestParam String searchBy,
                                       Pageable pageable) {
        if (searchBy.isEmpty()) {
            addAllProducts(modelAndView, pageable);
        } else {
            addAllProducts(modelAndView, searchBy, pageable);
        }
        modelAndView.setViewName("allProducts");
        return modelAndView;
    }

    @GetMapping("/filter")
    public ModelAndView searchProducts
            (ModelAndView modelAndView,
             @RequestParam ProductType productType,
             @RequestParam ProductTechnology productTechnology,
             Pageable pageable
            ) {
        modelAndView.addObject("page", service.filterByTypeAndTechnology(productType, productTechnology, pageable));
        modelAndView.setViewName("allProducts");
        return modelAndView;
    }

    private void addProducts(ModelAndView modelAndView, Iterable<Product> foundProducts) {
        final List<Product> products = StreamSupport.stream(foundProducts.spliterator(), false)
                .collect(Collectors.toList());
        modelAndView.addObject("products", products);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public ModelAndView delete(@ModelAttribute Product product,
                               ModelAndView modelAndView) {
        service.delete(product.getId());
        modelAndView.setViewName("redirect:" + "/products");
        return modelAndView;
    }


    private void addAllProducts(ModelAndView modelAndView, Pageable pageable) {
        Page<Product> page = service.getAll(pageable);
        List<Integer> pageSizes = List.of(5, 10, 15);
        int customPageSize = 0;
        modelAndView.addObject("customPageSize", customPageSize);
        modelAndView.addObject("page", page);
        modelAndView.addObject("pageSizes", pageSizes);
    }

    private void addAllProducts(ModelAndView modelAndView, String searchBy, Pageable pageable) {
        Page<Product> page = service.searchProducts(searchBy, pageable);
        final List<Product> products = StreamSupport.stream(service.searchProducts(searchBy, pageable).spliterator(), false)
                .collect(Collectors.toList());
        modelAndView.addObject("page", page);
    }

}
