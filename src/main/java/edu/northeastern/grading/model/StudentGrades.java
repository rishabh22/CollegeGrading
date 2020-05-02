package edu.northeastern.grading.model;

import lombok.Data;

@Data
public class StudentGrades {
    private Student student;
    private Marks marks;
    private Course course;

    public Double getAggregate() {
        return marks.getMarks().entrySet().stream()
                .mapToDouble(
                        mark ->
                                marks.getBestOf(mark.getKey(), course.getCriteriaVsCounted().get(mark.getKey())).getAsDouble()
                                        * course.getCriteriaVsWeightagePercentage().get(mark.getKey())
                ).average().getAsDouble();
    }
}
