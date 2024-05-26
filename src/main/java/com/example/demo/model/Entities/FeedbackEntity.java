package com.example.demo.model.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEntity {

    @Id
     private String id;
     private String username ;
     private String subject;
     private String feedbackContent;
     private String date;
     private String email;

}
