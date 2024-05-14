package com.example.blps_2.controller;

import com.example.blps_2.DTO.NewQuestionDTO;
import com.example.blps_2.DTO.UpdateQuestionDTO;
import com.example.blps_2.model.Question;
import com.example.blps_2.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;
    private final Logger logger;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
        this.logger = LoggerFactory.getLogger(QuestionController.class);
    }

    @PostMapping("/question")
    public Map<String, String> addNewQuestion(@Valid @RequestBody NewQuestionDTO question) {
        logger.info("Adding question: {}", question.getQuestionTitle());
        return questionService.saveQuestion(question);
    }

    @GetMapping("/question")
    public List<NewQuestionDTO> getAll() {
        logger.info("Getting all questions");
        return questionService.allQuestions();
    }


    @GetMapping("/question/{id}")
    public Question getById(@PathVariable("id") long id) {
        logger.info("Getting question: {}", id);
        return questionService.findById(id);
    }

    @PatchMapping("/question/{id}")
    public Map<String, String> updateById(@PathVariable("id") long id, @RequestBody UpdateQuestionDTO question) {
        logger.info("Updating question: {}", id);
        return questionService.updateQuestion(id, question);
    }

    @PatchMapping("/question/{id}/{rate}")
    public Map<String, String> rateById(@PathVariable("id") long id, @PathVariable("rate") boolean flag) {
        logger.info("Voting on question: {}", id);
        return questionService.updateQuestionRating(id, flag);
    }

    @DeleteMapping("/admin/question/{id}")
    public Map<String, String> delete(@PathVariable("id") long id) {
        logger.info("Deleting question: {}", id);
        questionService.deleteById(id);
        return Map.of("message", "success");
    }
}

