package com.Infotrixs.Payroll_System.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/admin/**")
                .permitAll()
                .requestMatchers("/employee/**")
                .permitAll()
                .requestMatchers("/admin-login/")
                .hasRole("ADMIN")
                .requestMatchers("/employee-login/")
                .hasRole("EMPLOYEE")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService getUserDetail(){
        return  new CustomUserDetailService();
    }
    @Bean
    public SecurityContextHolder getSecurityContextHolder(){
        return new SecurityContextHolder();
    }
    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetail());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }
}
