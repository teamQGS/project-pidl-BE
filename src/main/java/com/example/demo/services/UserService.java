package com.example.demo.services;

import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private  UserRepository repository;
    public List<UserEntity> getAllUsers(){
        return repository.findAll();
    }
    public Optional<UserEntity> getUserById(int id){
        return repository.findBy_numericalId(id);
    }
}
