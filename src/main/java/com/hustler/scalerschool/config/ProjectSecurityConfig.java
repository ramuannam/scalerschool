package com.hustler.scalerschool.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.beans.Introspector;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception{

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
//        http.csrf().disable().authorizeHttpRequests(auth -> auth
        http.csrf(csrf -> csrf
                        .ignoringRequestMatchers(new MvcRequestMatcher(introspector,"/saveMsg"))// Ignoring CSRF protection for this /saveMsg endpoint and for h2-console as well
//                        .ignoringRequestMatchers(new MvcRequestMatcher(introspector,"/courses"))
                        .ignoringRequestMatchers(PathRequest.toH2Console())  // Ignoring CSRF protection for h2-console as well
                ).authorizeHttpRequests((auth) -> auth
                        .requestMatchers(new MvcRequestMatcher(introspector,"/courses")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector,"/dashboard")).authenticated()
                        .requestMatchers(new MvcRequestMatcher(introspector,"/")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/home")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector,"/holidays/**")).permitAll() //   /** says that inside the /holidays all the urls/api are also included init to permit
                        .requestMatchers(new MvcRequestMatcher(introspector,"/contact")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector,"/login")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector,"/assets/**")).permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll() //making the h2-console to access by  everyone.

                )
//                .formLogin(withDefaults()) // default form based authentication
                .formLogin(loginConfigurer->loginConfigurer
                        .loginPage("/login")   // Custom login page
                        .defaultSuccessUrl("/dashboard")  // Redirect to dashboard on success
                        .failureUrl("/login?error=true")  // Redirect to login with error on failure
                        .permitAll()   // Allow everyone to access the login page
                )
                .logout(logoutConfigurer->logoutConfigurer  //this is the default logout way, which will not triggered as we enabled CSRF.so custom logout url get triggered as we implemented the method for logout in loginController.
//                        .logoutUrl("/logout")  // Ensure Spring Security listens to the same /logout endpoint,as this is the deafult method and acepts like POST method for security.
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))  // Allow GET request for logout, By default, Spring Security expects logout requests to be POST requests. If your controller is handling /logout with a GET request, it won’t align with Spring’s expectations, resulting in a 403 error.
                        .logoutSuccessUrl("/login?logout=true")  // Redirect after successful logout
                        .invalidateHttpSession(true)            // Invalidate the session
                        .deleteCookies("JSESSIONID")   // Optional: Delete cookies for a clean logout
                        .permitAll()


                )
                .httpBasic(Customizer.withDefaults()); //basic authentication both will be applied for above endpoints as per their security.

                 // Disabling frame options for H2 console access
                 http.headers(headersConfigurer -> headersConfigurer
                         .frameOptions(frameOptionConfig -> frameOptionConfig.disable()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //BCrypt password encryption.
//        return NoOpPasswordEncoder.getInstance();  // No encryption - stores plain text passwords
    }

    //Non-persistent implementation of UserDetailsManager which is backed by an in-memory map.
    //Mainly intended for testing and demonstration purposes, where a full blown persistent system isn't required.
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        PasswordEncoder encoder =passwordEncoder();
        UserDetails user = User.builder() //this user class have withDeafultPasswordEncoder() whcih stores the passowrd in a plainn text, we have other also like hashing for securing.
                .username("user")
                .password(encoder.encode("12345"))
                .roles("USER")
                .build();
        UserDetails admin=User.builder()
                .username("admin")
                .password(encoder.encode("54321"))
                .roles("USER","ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin); //it is a variable constructor here as it can accept any no of users to create a  new user object and stores in the in-memory map.(look into its implementation
    }
}
