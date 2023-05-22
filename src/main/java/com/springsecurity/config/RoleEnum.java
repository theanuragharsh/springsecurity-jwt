package com.springsecurity.config;

import com.google.common.collect.Sets;

import static com.springsecurity.config.UserRolePermissions.*;

import java.util.Set;

public enum RoleEnum {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    private final Set<UserRolePermissions> permissions;

    RoleEnum(Set<UserRolePermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserRolePermissions> getPermissions() {
        return permissions;
    }
}
