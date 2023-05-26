package com.springsecurity.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final ApplicationUserDetailsDao applicationUserDetailsDao;

    public ApplicationUserDetailsService(@Qualifier("fake") ApplicationUserDetailsDao applicationUserDetailsDao) {
        this.applicationUserDetailsDao = applicationUserDetailsDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.applicationUserDetailsDao
                .findApplicationUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found for username : %s ", username)));
    }
}
