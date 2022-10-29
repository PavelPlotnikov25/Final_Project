package com.plotnikov.project.filter;

import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.model.product.ProductTechnology;
import com.plotnikov.project.model.product.ProductType;
import com.plotnikov.project.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;


@Component
@Slf4j
public class AddProductTypeFilter implements Filter {

    @Autowired
    CustomerService customerService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        if (!Objects.isNull(httpServletRequest.getUserPrincipal())) {
            Principal principal = httpServletRequest.getUserPrincipal();
            Customer customer = customerService.getCustomerByPrincipal(principal);
            if(customer!=null){
                session.setAttribute("userRole", customer.getRole().toString());
            }
        }
        if (Objects.isNull(session.getAttribute("productTypes")) ||
                Objects.isNull(session.getAttribute("productTechnologies"))) {
            session.setAttribute("productTypes", List.of(ProductType.values()));
            session.setAttribute("productTechnologies", List.of(ProductTechnology.values()));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
