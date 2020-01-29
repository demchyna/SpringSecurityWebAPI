package com.softserve.academy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private UsernamePasswordAuthenticationFilter webAuthenticationFilter;
    private AbstractAuthenticationProcessingFilter webApiFilter;

    @Autowired
    public void setWebAuthenticationFilter(UsernamePasswordAuthenticationFilter webAuthenticationFilter) {
        this.webAuthenticationFilter = webAuthenticationFilter;
    }

    @Autowired
    public void setWebApiFilter(@Qualifier("webApiFilter") AbstractAuthenticationProcessingFilter webApiFilter) {
        this.webApiFilter = webApiFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .anyRequest().authenticated();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors();

        http.httpBasic().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .csrf().disable();

        http.addFilterAt(webAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(webApiFilter, WebAuthenticationFilter.class);

//        http.exceptionHandling().accessDeniedHandler(
//                (request, response, accessDeniedException) -> response.sendRedirect("/access-denied"));
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(HttpMethod.GET, "/login");
//    }

    @Bean("webApiFilter")
    @Autowired
    public AbstractAuthenticationProcessingFilter getWebApiFilter(@Qualifier("webApiManager") AuthenticationManager webApiManager) {
        AbstractAuthenticationProcessingFilter webApiFilter = new WebApiFilter("/**");
        webApiFilter.setAuthenticationManager(webApiManager);
        return webApiFilter;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
