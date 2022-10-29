package com.plotnikov.project.config;

import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.repository.CustomerRepository;
import com.plotnikov.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomerRepository repository;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(CustomerRepository repository, CustomerService customerService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/basket").authenticated()
                .antMatchers("/users", "/create", "/update").hasAuthority("ADMIN")
                .antMatchers("/orders").hasRole("USER")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").loginPage("/products").loginProcessingUrl("/login").permitAll()
                .and()
                .rememberMe()
                .and()
                .logout().permitAll()
                .invalidateHttpSession(true)
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
                    @Override
                    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                        Customer customer = repository.findByEmail(email).orElse(null);
                        if (Objects.isNull(customer)){
                            throw new UsernameNotFoundException("user not found");
                        }

                        return new User(customer.getEmail(), customer.getPassword(), List.of(new SimpleGrantedAuthority(customer.getRole().toString())));
                    }
                })
                .passwordEncoder(passwordEncoder);
    }

}