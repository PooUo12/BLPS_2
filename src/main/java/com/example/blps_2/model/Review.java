package com.example.blps_2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Data;


import java.sql.Timestamp;

@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "author")
    private String author;

    @Column(name = "text")
    @Size(max = 254, message = "text too long")
    private String text;

    @Column(name = "timestamp")
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Column(name = "rating")
    private int rating;

    @JsonIgnore
    @Column(name = "question_id")
    long questionId;
}
