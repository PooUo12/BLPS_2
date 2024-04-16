package com.example.blps_2.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuestionDTO {
    private String questionDescription;
    private List<Long> tags;
}

