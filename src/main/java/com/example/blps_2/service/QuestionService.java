package com.example.blps_2.service;

import com.example.blps_2.DTO.NewQuestionDTO;
import com.example.blps_2.DTO.UpdateQuestionDTO;
import com.example.blps_2.model.Question;
import com.example.blps_2.model.Tag;
import com.example.blps_2.repository.QuestionRepository;
import com.example.blps_2.repository.TagRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
    }

    public List<NewQuestionDTO> allQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<NewQuestionDTO> answer = new ArrayList<>();
        for(Question question : questions) {
            List<Long> tags = new ArrayList<>();
            for(Tag tag : question.getTags()) {
                tags.add(tag.getId());
            }
            answer.add(
                    new NewQuestionDTO(question.getQuestionTitle(), question.getQuestionDescription(), tags)
            );
        }
        return answer;
    }

    public Question findById(long id) {
        var question = questionRepository.findById(id);
        return question.orElse(null);
    }

    @Transactional
    public Map<String, String> saveQuestion(NewQuestionDTO newquestion) {
        newquestion.setQuestionTitle(newquestion.getQuestionTitle().strip());
        newquestion.setQuestionDescription(newquestion.getQuestionDescription().strip());
        Question questionStored = questionRepository.findByQuestionTitle(newquestion.getQuestionTitle());
        if (questionStored != null) {
            return Map.of("message", "failure - question already exists");
        }
        if (newquestion.getTags() == null) {
            newquestion.setTags(new ArrayList<>());
        }
        List<Tag> questionTags = checkTags(newquestion.getTags());
        if (questionTags == null) {
            return Map.of("message", "failure - no tags provided");
        }
        Question question = new Question();
        question.setQuestionTitle(newquestion.getQuestionTitle());
        question.setQuestionDescription(newquestion.getQuestionDescription());
        question.setTags(questionTags);
        if (question.getReviews() == null) {
            question.setReviews(new HashSet<>());
        }
        questionRepository.save(question);
        return Map.of("message", "success");
    }

    @Transactional
    public Map<String, String> updateQuestion(long id, UpdateQuestionDTO updatequestion) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        if(!auth.getName().equals(questionRepository.findById(id).orElse(new Question()).getUsername())){
            return Map.of("message", "failure - you are not author of this question");
        }
        Question questionStored = questionRepository.findById(id).orElse(null);
        if (questionStored == null) {
            return Map.of("message", "failure - no such question");
        }
        if (updatequestion.getQuestionDescription() != null) {
            questionStored.setQuestionDescription(updatequestion.getQuestionDescription());
        }
        if (updatequestion.getTags() != null) {
            List<Tag> questionTags = checkTags(updatequestion.getTags());
            if (questionTags == null) {
                return Map.of("message", "failure - some tags are incorrect");
            }
            questionStored.setTags(questionTags);
        }
        questionRepository.save(questionStored);
        return Map.of("message", "success");
    }

    @Transactional
    public Map<String, String> updateQuestionRating(long id, boolean flag){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName().equals(questionRepository.findById(id).orElse(new Question()).getUsername())){
            return Map.of("message", "failure - you cant vote on your own question");
        }
        Question questionStored = questionRepository.findById(id).orElse(null);
        if (questionStored == null) {
            return Map.of("message", "failure - no such question");
        }
        if(flag){
            questionStored.setRating(questionStored.getRating()+1);
        }
        else {
            questionStored.setRating(questionStored.getRating()-1);
        }
        questionRepository.save(questionStored);
        return Map.of("message", "success");
    }

    private List<Tag> checkTags(List<Long> tags) {
        List<Tag> questionTags = new ArrayList<>();
        for (long tagId : tags) {
            Tag tagStored = tagRepository.findById(tagId).orElse(null);
            questionTags.add(tagStored);
            if (tagStored == null) {
                return null;
            }
        }
        return questionTags;
    }

    @Transactional
    public void deleteById(long id) {
        questionRepository.deleteById(id);
    }
}
