package com.example.demo.model.Entities.Enums;

// This enum represents the different roles that a user can have
public enum Role {
    ADMIN, // This is the only role that can create new roles and manage users
    MANAGER, // This role is for employees that can manage the products and orders
    CUSTOMER, // Just registered users with no special permissions
    GUEST // Users that are not registered
}
