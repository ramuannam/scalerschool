package com.hustler.scalerschool.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        /*
        permitAll() =>  Public endpoints
        authenticated() => Secure endpoints
        denyAll()  => denies all
         */

        /*
         anyRequest() => means you are configuring something which is specific to your entire application.(not for production)
         so use requestMatchers() =>  for production case, as it tries to match the endpoint.
         */

        //custom config at api  level (it is good approach to use seperate requestMatcher for each api, than directly to one, as if you use that same specific directly in a single requestMatcher() sometimes may cause allowing the random endpoints access)
        http.csrf().disable().authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/home").permitAll()
                        .requestMatchers("/holidays/**").permitAll() //   /** says that inside the /holidays all the urls/api are also included init to permit
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/saveMsg").permitAll()
                        .requestMatchers("/courses").authenticated() //made this courses page secured, so any user have to make Authentication to authorize the page.
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                )
                .formLogin(withDefaults()) //form based authentication
                .httpBasic(withDefaults()); //basic authentication both will be applied for above endpoints as per their security.

        return http.build();


        //-------------------------
        //this will Permits All requests inside wen app without any security.
//        http.authorizeHttpRequests(requests->requests.anyRequest().permitAll())
//        .formLogin(withDefaults())
//        .httpBasic(withDefaults());
//        return http.build();

        //---------------------------------


       ////ths denys all the api after the user is authenticated.
//        http.authorizeHttpRequests(requests->requests.anyRequest().denyAll())
//                .formLogin(withDefaults())
//                .httpBasic(withDefaults());
//        return http.build();
    }
}
