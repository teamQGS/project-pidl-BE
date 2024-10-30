package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
