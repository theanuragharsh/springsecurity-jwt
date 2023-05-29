package com.springsecurity.auth;

public interface FakeApplicationUserDao {
    ApplicationUserDetails findUserByUsername(String username);
}
