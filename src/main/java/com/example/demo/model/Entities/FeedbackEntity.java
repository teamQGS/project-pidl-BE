package com.example.demo.model.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEntity {

     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE)
     private Long id;
     private String username ;
     private String subject;
     private String feedbackContent;
     private String date;
     private String email;

}
