
package com.example.demo.model.entities.enums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_DELETE("admin:delete"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    MANAGER_DELETE("manager:delete"),
    MANAGER_READ("admin:read"),
    MANAGER_UPDATE("admin:update"),
    MANAGER_CREATE("admin:create")
    ;

    private final String permission;

}
