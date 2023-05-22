package com.springsecurity.config;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.springsecurity.config.UserRolePermissions.*;

import java.util.Set;
import java.util.stream.Collectors;

public enum UserRoleEnum {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    private final Set<UserRolePermissions> permissions; //This Set holds the permissions associated with the role.

    UserRoleEnum(Set<UserRolePermissions> permissions) {
        this.permissions = permissions;
    }

    //This method returns the set of permissions associated with the role.
    public Set<UserRolePermissions> getPermissions() {
        return permissions;
    }

    /*    This will map all the roles and permissions into a SimpleGrantedAuthority
        The SimpleGrantedAuthority is a class from Spring Security that represents a granted authority or permission.
        By mapping the roles and permissions into SimpleGrantedAuthority objects,
        they can be used for authorization and access control purposes.*/
    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getPermissions().stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getPermissions()))
                .collect(Collectors.toSet());
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return simpleGrantedAuthorities;
    }
}
