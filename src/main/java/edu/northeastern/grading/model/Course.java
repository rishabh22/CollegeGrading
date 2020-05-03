package edu.northeastern.grading.model;

import edu.northeastern.grading.SharedContext;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class Course {
    private String name;

    private Map<GradingCriteria, Double> criteriaVsWeightagePercentage;
    private Map<GradingCriteria, Integer> criteriaVsCounted;

    @Setter
    private GradeMode gradingMode;

    private Map<Grade, Double> gradeMarksMap;

    private Course() {
    }

    public static CourseBuilder getBuilder(String courseName, GradeMode gradingMode, boolean equalWeightages) {
        return new CourseBuilder(courseName, gradingMode, equalWeightages);
    }

    public static class CourseBuilder {

        private final String courseName;
        private final GradeMode gradingMode;

        private final boolean equalWeightages;

        private Map<GradingCriteria, Double> criteriaVsWeightagePercentage;
        private Map<GradingCriteria, Integer> criteriaVsCounted;
        private Map<Grade, Double> gradeMarksMap;
        private Double autoGradeInterval;

        public CourseBuilder(String courseName, GradeMode gradingMode, boolean equalWeightages) {
            this.courseName = courseName;
            this.gradingMode = gradingMode;
            this.equalWeightages = equalWeightages;
        }

        public void setAutoAbsoluteGradeInterval(double interval) {
            autoGradeInterval = interval;
        }

        public void addCriteria(GradingCriteria criteria, int counted) {
            if (criteriaVsCounted == null) {
                criteriaVsCounted = new HashMap<>();
            }

            if (criteriaVsCounted.containsKey(criteria)) {
                throw new IllegalStateException("Criteria already Exists");
            }

            criteriaVsCounted.put(criteria, counted);

        }

        public void addWeightage(GradingCriteria criteria, double weightagePercentage) {

            if (equalWeightages) {
                throw new IllegalStateException("Weightage mode set to equal");
            }

            if (criteriaVsCounted == null) {
                throw new IllegalStateException("Consideration counts not initialized");
            }

            if (!criteriaVsCounted.containsKey(criteria)) {
                throw new IllegalStateException("Consideration count not added for given criteria");
            }

            if (criteriaVsWeightagePercentage == null) {
                criteriaVsWeightagePercentage = new HashMap<>();
            }

            criteriaVsWeightagePercentage.put(criteria, weightagePercentage);

        }


        public Course getInstance() {
            if (criteriaVsCounted == null) {
                throw new IllegalStateException("Consideration counts not initialized");
            }

            if (equalWeightages) {
                criteriaVsWeightagePercentage = new HashMap<>();
                criteriaVsCounted.keySet().forEach(key -> {
                    criteriaVsWeightagePercentage.put(key, (double) 100 / criteriaVsCounted.entrySet().stream().filter(entry -> entry.getValue() > 0).count());
                });
            } else if (criteriaVsWeightagePercentage == null) {
                throw new IllegalStateException("Weightages not equal and not initialized");
            }


            if (gradingMode.equals(GradeMode.ABSOLUTE_AUTO)) {
                if (autoGradeInterval == null) {
                    throw new IllegalStateException("Grading Interval not initialized");
                }
                gradeMarksMap = new LinkedHashMap<>();
                double currCutOff = 100;
                for (Grade grade : SharedContext.getInstance().getGradeList()) {
                    currCutOff -= autoGradeInterval;
                    if(grade.equals(SharedContext.getInstance().getGrade("F"))) {
                        gradeMarksMap.put(grade, 0D);
                        break;
                    } else {
                        gradeMarksMap.put(grade, currCutOff);
                    }
                }
            } else if(gradingMode.equals(GradeMode.ABSOLUTE_CUSTOM)) {
                if (gradeMarksMap == null) {
                    throw new IllegalStateException("Custom Grading not initialized");
                }
            }

            Course course = new Course();
            course.name = courseName;
            course.gradingMode = gradingMode;

            course.criteriaVsCounted = criteriaVsCounted;
            course.criteriaVsWeightagePercentage = criteriaVsWeightagePercentage;
            course.gradeMarksMap = gradeMarksMap;

            return course;
        }
    }

}
