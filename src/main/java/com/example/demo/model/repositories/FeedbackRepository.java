package com.example.demo.model.repositories;

import com.example.demo.model.entities.FeedbackEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends MongoRepository <FeedbackEntity, String>{

}
