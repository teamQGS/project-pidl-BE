package com.example.demo.model.Entities.Enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(Set.of(
            Permission.ADMIN_DELETE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_READ,
            Permission.ADMIN_CREATE
    )
    ), // This is the only role that can create new roles and manage users
    MANAGER(Set.of(
            Permission.MANAGER_DELETE,
            Permission.MANAGER_CREATE,
            Permission.MANAGER_UPDATE,
            Permission.MANAGER_READ
    )
    ), // This role is for employees that can manage the products and orders
    CUSTOMER(Collections.emptySet()); // Just registered users with no special permissions
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities =  getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
