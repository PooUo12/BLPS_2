package com.example.blps_2.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    @NotBlank(message = "question title is mandatory")
    @NotEmpty(message = "question title can't be empty")
    private String questionTitle;

    @Column(name = "description")
    @NotBlank(message = "question description is mandatory")
    @NotEmpty(message = "question description can't be empty")
    private String questionDescription;

    @Column(name = "rating")
    private int rating;

    @ManyToMany
    List<Tag> tags;

    @OneToMany
    @JoinColumn(name = "question_id")
    Set<Review> reviews = new HashSet<>();

    public void removeTags(Tag tag) {
        tags.remove(tag);
        tag.getQuestions().remove(this);
    }
}
