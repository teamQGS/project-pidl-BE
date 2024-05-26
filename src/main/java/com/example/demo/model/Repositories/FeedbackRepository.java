package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.FeedbackEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends MongoRepository <FeedbackEntity, String>{

}
