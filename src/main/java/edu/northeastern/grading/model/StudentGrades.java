package edu.northeastern.grading.model;

import lombok.Data;

@Data
public class StudentGrades {
    private Student student;
    private Marks marks;
    private Course course;

    public Double getAggregate(){
        return marks.getBestOf(marks.getAssignmentMarks(),course.getConsideredAssignmentCount()) * course.getAssignmentWeightage()
                + marks.getBestOf(marks.getQuizMarks(),course.getConsideredQuizCount()) * course.getQuizWeightage()
                + marks.getBestOf(marks.getMidTermExamMarks(),course.getConsideredMidTermExamCount()) * course.getMidTermExamWeightage()
                + marks.getBestOf(marks.getProjectMarks(),course.getConsideredProjectCount()) * course.getProjectWeightage()
                + marks.getBestOf(marks.getFinalExamMarks(),course.getConsideredFinalExamCount()) * course.getFinalExamWeightage()
                + marks.getParticipation() * course.getParticipationWeightage();
    }
}
