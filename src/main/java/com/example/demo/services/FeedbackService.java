package com.example.demo.services;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.model.entities.FeedbackEntity;
import com.example.demo.model.repositories.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final ModelMapper modelMapper;
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
