package com.example.demo.services;

import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private  UserRepository repository;
    public List<UserEntity> getAllUsers(){
        return repository.findAll();
    }
}
