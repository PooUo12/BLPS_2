package com.example.blps_2.controller;

import com.example.blps_2.model.Review;
import com.example.blps_2.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/question/{id}/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public Map<String, String> addNewReview(@PathVariable("id") long id, @Valid @RequestBody Review review) {
        return reviewService.saveReview(id, review);
    }

    @PatchMapping("/{rid}/{rate}")
    public Map<String, String> rateById(@PathVariable("rid") long id, @PathVariable("rate") boolean flag) {
        return reviewService.updateReviewRating(id, flag);
    }

}
