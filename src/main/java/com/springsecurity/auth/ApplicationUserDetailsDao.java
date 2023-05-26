package com.springsecurity.auth;

import java.util.Optional;

public interface ApplicationUserDetailsDao {
    Optional<ApplicationUserDetails> findApplicationUserByUsername(String username);
}
