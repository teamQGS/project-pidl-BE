package com.example.demo.model.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Table(name = "feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEntity {

     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE)
     private long id;
     private String username ;
     private String subject;
     private String feedbackContent;
     private String date;
     private String email;

}
