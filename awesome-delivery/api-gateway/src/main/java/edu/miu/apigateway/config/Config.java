package edu.miu.apigateway.config;

import edu.miu.apigateway.constants.RestEndpoints;
import edu.miu.apigateway.utils.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(
                        "/"+ RestEndpoints.ACCOUNT_PREFIX+RestEndpoints.REGISTER,
                        "/"+ RestEndpoints.ACCOUNT_PREFIX+RestEndpoints.LOGIN
                ).permitAll()
                .antMatchers(
                        // Account Endpoints
                        "/"+ RestEndpoints.ACCOUNT_PREFIX+RestEndpoints.CURRENT,
                        // Restaurant Endpoints
                        "/"+ RestEndpoints.RESTAURANT_PREFIX+RestEndpoints.RES_ID+RestEndpoints.MENUS,
                        "/"+RestEndpoints.RESTAURANT_PREFIX+RestEndpoints.RES_ID+RestEndpoints.MENUS+RestEndpoints.MENU_ID,
                        "/"+RestEndpoints.RESTAURANT_PREFIX+RestEndpoints.RES_ID+RestEndpoints.RES_ORDERS,
                        "/"+RestEndpoints.RESTAURANT_PREFIX+RestEndpoints.RES_ID+RestEndpoints.RES_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.ACCEPT,
                        "/"+RestEndpoints.RESTAURANT_PREFIX+RestEndpoints.RES_ID+RestEndpoints.RES_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.REJECT,
                        "/"+RestEndpoints.RESTAURANT_PREFIX+RestEndpoints.RES_ID+RestEndpoints.RES_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.READY,

                        // Customer Endpoints
                        "/"+RestEndpoints.CUSTOMER_PREFIX+RestEndpoints.RESTAURANTS,
                        "/"+RestEndpoints.CUSTOMER_PREFIX+RestEndpoints.RESTAURANTS+RestEndpoints.SEARCH,
                        "/"+RestEndpoints.CUSTOMER_PREFIX+RestEndpoints.CUS_ID+RestEndpoints.CUS_ORDERS,

                        // Driver Endpoints
                        "/"+RestEndpoints.DRIVER_PREFIX+RestEndpoints.DRI_ID+RestEndpoints.DRI_ORDERS,
                        "/"+RestEndpoints.DRIVER_PREFIX+RestEndpoints.DRI_ID+RestEndpoints.DRI_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.PICK,
                        "/"+RestEndpoints.DRIVER_PREFIX+RestEndpoints.DRI_ID+RestEndpoints.DRI_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.DELIVER

                ).authenticated().and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
