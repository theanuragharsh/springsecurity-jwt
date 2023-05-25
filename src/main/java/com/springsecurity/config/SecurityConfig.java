package com.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

 import java.time.Duration;

import static com.springsecurity.config.UserRoleEnum.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/index").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
/*              .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())*/
                .anyRequest()
                .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses", true) //redirects to this page after logged in successfully
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds((int) Duration.ofDays(21).toSeconds()) //default duration is 2 weeks
                    .key("something-very-secured")
                .and()
                    .logout()
                    .logoutUrl("/login")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.name()))
//                    When CSRF is disabled you can specify any method as logging-out request but
//                    when enabled it should only be a POST request with the valid CSRF token
                    .clearAuthentication(true) // Clears authentication of the current logged-in user
                    .deleteCookies("JSESSIONID","remember-me") //Clears cookies
                    .logoutSuccessUrl("/login"); // redirects to this url when logged out

    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        final UserDetails userAnna = User.builder().username("anna")
                .password(passwordEncoder().encode("password"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getAuthorities())
                .build();
        final UserDetails userLinda = User.builder().username("linda")
                .password(passwordEncoder().encode("password"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getAuthorities())
                .build();
        final UserDetails userTom = User.builder().username("tom")
                .password(passwordEncoder().encode("password"))
//                .roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getAuthorities())
                .build();
        return new InMemoryUserDetailsManager(userAnna, userLinda, userTom);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
