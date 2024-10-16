package com.example.demo.services;

import com.example.demo.DTOS.FeedbackDTO;
import com.example.demo.DTOS.ProductDTO;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.model.Entities.FeedbackEntity;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.FeedbackRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    ModelMapper modelMapper;
    public List<FeedbackDTO> getAllFeedbacks(){
        List<FeedbackEntity> feedbackEntities = feedbackRepository.findAll();
        return feedbackEntities.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public FeedbackDTO convertToDTO(FeedbackEntity feedbackEntity){
        return modelMapper.map( feedbackEntity, FeedbackDTO.class);
    }
    public FeedbackDTO createProduct(FeedbackDTO feedbackDTO){
        FeedbackEntity feedbackEntity = modelMapper.map(feedbackDTO, FeedbackEntity.class);
        FeedbackEntity savedFeedback = feedbackRepository.save(feedbackEntity);
        return modelMapper.map(savedFeedback, FeedbackDTO.class);
    }


}
