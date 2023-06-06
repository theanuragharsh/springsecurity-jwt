package com.springsecurity.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.springsecurity.config.UserRoleEnum.*;

@Repository("fake")
public class FakeApplicationUserDaoImpl implements FakeApplicationUserDao {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${user.password}")
    private String password;

    @Override
    public ApplicationUserDetails findUserByUsername(String username) {
        return provideUserDetails()
                .stream()
                .filter(applicationUserDetails -> username.equals(applicationUserDetails.getUsername()))
                .findAny()
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username : %s not found..!", username)));
    }

    private List<ApplicationUserDetails> provideUserDetails() {
        return Lists.newArrayList(
                new ApplicationUserDetails("anna", passwordEncoder.encode(password),
                        STUDENT.getAuthorities(),
                        true, true, true, true),
                new ApplicationUserDetails("linda", passwordEncoder.encode(password),
                        ADMIN.getAuthorities(),
                        true, true, true, true),
                new ApplicationUserDetails("tom", passwordEncoder.encode(password),
                        ADMINTRAINEE.getAuthorities(),
                        true, true, true, true)
        );
    }
}
