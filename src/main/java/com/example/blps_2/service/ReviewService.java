package com.example.blps_2.service;


import com.example.blps_2.model.Question;
import com.example.blps_2.model.Review;
import com.example.blps_2.repository.QuestionRepository;
import com.example.blps_2.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final QuestionRepository questionRepository;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository, QuestionRepository questionRepository) {
        this.reviewRepository = reviewRepository;
        this.questionRepository = questionRepository;
    }

    public Map<String, String> saveReview(long questionId, Review review) {
        Question questionStored = questionRepository.findById(questionId).orElse(null);
        if (questionStored == null) {
            return Map.of("message", "failure - question not found");
        }
        review.setQuestionId(questionStored.getId());
        reviewRepository.save(review);
        return Map.of("message", "success");
    }

    @Transactional
    public Map<String, String> updateReviewRating(long id, boolean flag){
        Review reviewStored = reviewRepository.findById(id).orElse(null);
        if (reviewStored == null) {
            return Map.of("message", "failure - no such review");
        }
        if(flag){
            reviewStored.setRating(reviewStored.getRating()+1);
        }
        else {
            reviewStored.setRating(reviewStored.getRating()-1);
        }
        reviewRepository.save(reviewStored);
        return Map.of("message", "success");
    }
}

