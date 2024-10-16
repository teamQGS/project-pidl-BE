package com.example.demo.controllers;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks(){
        return new ResponseEntity<>(feedbackService.getAllFeedbacks(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Optional<FeedbackDTO>> addProduct(@RequestBody FeedbackDTO feedbackDTO){
        return new ResponseEntity<>(Optional.ofNullable(feedbackService.createProduct(feedbackDTO)), HttpStatus.OK);
    }
}
