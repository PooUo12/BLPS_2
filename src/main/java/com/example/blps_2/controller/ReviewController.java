package com.example.blps_2.controller;

import com.example.blps_2.model.Review;
import com.example.blps_2.service.ReviewService;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/question/{id}/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final Logger logger;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
        this.logger = LoggerFactory.getLogger(ReviewController.class);
    }

    @PostMapping
    public Map<String, String> addNewReview(@PathVariable("id") long id, @Valid @RequestBody Review review) {
        logger.info("Adding review on question: {}", id);
        return reviewService.saveReview(id, review);
    }

    @PatchMapping("/{rid}/{rate}")
    public Map<String, String> rateById(@PathVariable("rid") long id, @PathVariable("rate") boolean flag) {
        logger.info("Voting on review: {}", id);
        return reviewService.updateReviewRating(id, flag);
    }

}
