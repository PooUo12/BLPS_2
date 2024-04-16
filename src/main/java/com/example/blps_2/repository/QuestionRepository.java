package com.example.blps_2.repository;

import com.example.blps_2.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByQuestionTitle(String title);
}

