package com.example.blps_2.controller;

import com.example.blps_2.model.Tag;
import com.example.blps_2.service.TagService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;
    private final Logger logger;

    public TagController(TagService tagService) {
        this.tagService = tagService;
        this.logger = LoggerFactory.getLogger(TagController.class);
    }

    @PostMapping("/admin/tag")
    public Map<String, String> addTag(@Valid @RequestBody Tag tag) {
        logger.info("Adding tag: {}", tag.getName());
        return tagService.saveTag(tag);
    }

    @GetMapping("/tag")
    public List<Tag> getAll() {
        logger.info("Getting all tags");
        return tagService.allTags();
    }


    @GetMapping("/tag/{id}")
    public Tag get(@PathVariable("id") long id) {
        //TODO: tag not found
        logger.info("Getting tag: {}", id);
        return tagService.findById(id);
    }

    @DeleteMapping("/admin/tag/{id}")
    public Map<String, String> deleteOne(@PathVariable("id") long id) {
        logger.info("Deleting tag: {}", id);
        return Map.of("message", tagService.deleteById(id));
    }
}
