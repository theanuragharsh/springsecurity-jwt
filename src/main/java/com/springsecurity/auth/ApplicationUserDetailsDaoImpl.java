package com.springsecurity.auth;

import com.google.common.collect.Lists;
import com.springsecurity.config.UserRoleEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fake")
public class ApplicationUserDetailsDaoImpl implements ApplicationUserDetailsDao {

    private static final String PASSWORD = "password";

/*    private final PasswordEncoder passwordEncoder;

    public ApplicationUserDetailsDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }*/

    @Override
    public Optional<ApplicationUserDetails> findApplicationUserByUsername(String username) {

        return getApplicationUsers().stream()
                .filter(applicationUserDetails -> username.equals(applicationUserDetails.getUsername())).findAny();

    }

    private List<ApplicationUserDetails> getApplicationUsers() {
        return Lists.newArrayList(
                new ApplicationUserDetails("anna", PASSWORD, UserRoleEnum.STUDENT.getAuthorities(), true, true, true, true),
                new ApplicationUserDetails("linda", PASSWORD, UserRoleEnum.ADMIN.getAuthorities(), true, true, true, true),
                new ApplicationUserDetails("tom", PASSWORD, UserRoleEnum.ADMINTRAINEE.getAuthorities(), true, true, true, true));
    }
}
