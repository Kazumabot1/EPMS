package com.epms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "form_questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String questionText;    // The actual question text
    private String responseType;    // The type of response (e.g., rating, text, yes/no)
    private Boolean isRequired;     // Whether the question is mandatory or optional

    @OneToMany(mappedBy = "question")
    private List<AppraisalAnswer> answers; // List of answers for this question
}