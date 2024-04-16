package com.example.blps_2.service;

import com.example.blps_2.model.Question;
import com.example.blps_2.model.Tag;
import com.example.blps_2.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> allTags() {
        return tagRepository.findAll();
    }

    public Tag findById(long id) {
        return tagRepository.findById(id).orElse(new Tag());
    }

    @Transactional
    public Map<String, String> saveTag(Tag tag) {
        tag.setName(tag.getName().strip());
        Tag tagStored = tagRepository.findByName(tag.getName());
        if (tagStored != null) {
            return Map.of("message", "failure - tag already exists");
        }
        if (tag.getQuestions() == null) {
            tag.setQuestions(new HashSet<>());
        }
        tagRepository.save(tag);
        return Map.of("message", "success");
    }

    @Transactional
    public void deleteById(long id) {
        Tag tag = tagRepository.findById(id).orElse(new Tag());
        tagRepository.deleteById(id);
    }

}
