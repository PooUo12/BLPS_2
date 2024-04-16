package com.example.blps_2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "full name is mandatory")
    @NotEmpty(message = "full name can't be empty")
    private String name;

    @JsonIgnore
    @Transient
    @ManyToMany
    Set<Question> questions = new HashSet<>();

    public void removeQuestion(Question question) {
        this.questions.remove(question);
        question.getTags().remove(this);
    }

    @PreRemove
    public void preRemove() {
        for(Question el : this.getQuestions()){
            this.removeQuestion(el);
        }
    }

}
