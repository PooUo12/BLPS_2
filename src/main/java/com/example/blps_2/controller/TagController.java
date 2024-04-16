package com.example.blps_2.controller;

import com.example.blps_2.model.Tag;
import com.example.blps_2.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/admin/tag")
    public Map<String, String> addTag(@Valid @RequestBody Tag tag) {
        return tagService.saveTag(tag);
    }

    @GetMapping("/tag")
    public List<Tag> getAll() {
        return tagService.allTags();
    }


    @GetMapping("/tag/{id}")
    public Tag get(@PathVariable("id") long id) {
        return tagService.findById(id);
    }

    @DeleteMapping("/admin/tag/{id}")
    public Map<String, String> deleteOne(@PathVariable("id") long id) {
        tagService.deleteById(id);
        return Map.of("message", "success");
    }
}
