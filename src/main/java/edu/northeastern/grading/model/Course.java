package edu.northeastern.grading.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Course {
    private String name;

    Map<GradingCriteria, Double> criteriaVsWeightage = new HashMap<>();
    Map<GradingCriteria, Integer> criteriaVsCounted = new HashMap<>();

    @Setter
    private GradeMode gradingMode;

    private Course() {
    }

    public static CourseBuilder getBuilder(String courseName, GradeMode gradingMode, boolean equalWeightages) {
        return new CourseBuilder(courseName, gradingMode, equalWeightages);
    }

    public static class CourseBuilder {

        private final String courseName;
        private final GradeMode gradingMode;

        private final boolean equalWeightages;

        private Map<GradingCriteria, Double> criteriaVsWeightage = new HashMap<>();

        public CourseBuilder(String courseName, GradeMode gradingMode, boolean equalWeightages) {
            this.courseName = courseName;
            this.gradingMode = gradingMode;
            this.equalWeightages = equalWeightages;
        }




        public Course getInstance() {
            /*if (consideredAssignmentCount == null) {
                throw new IllegalStateException("Consideration counts not initialized");
            }*/

            Course course = new Course();
            course.name = courseName;
            course.gradingMode = gradingMode;

            if (equalWeightages) {

            }

            return course;
        }
    }

}
