package com.example.demo.controllers;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks(){
        return new ResponseEntity<>(feedbackService.getAllFeedbacks(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Optional<FeedbackDTO>> addProduct(@RequestBody FeedbackDTO feedbackDTO){
        return new ResponseEntity<>(Optional.ofNullable(feedbackService.createProduct(feedbackDTO)), HttpStatus.OK);
    }
}
