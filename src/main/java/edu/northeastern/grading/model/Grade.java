package edu.northeastern.grading.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Grade {
    private final String letterGrade;

    private final Float numericalEquivalent;

    private final String explanation;

}
