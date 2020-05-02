package edu.northeastern.grading;

import edu.northeastern.grading.model.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.Set;

public class Application extends javafx.application.Application {
    private SharedContext app;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Grading System");

        createTestData();

        Parent page = FXMLLoader.load(getClass().getResource(ViewFXMLs.RANKING_TABLE), null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page);

        stage.setScene(scene);
        stage.sizeToScene();
        SharedContext.getInstance().setCurrentStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void createTestData() {
        Course.CourseBuilder courseBuilder = Course.getBuilder("Program Structures and Algorithms");
        courseBuilder.setConsideredCounts(-1,-1,-1,-1,-1);
        Course course = courseBuilder.getInstance();

        Student student = new Student("001300579", "Rishabh Sood");
        app.addStudent(student);

        StudentGrades studentGrades = new StudentGrades();
        studentGrades.setStudent(student);
        studentGrades.setCourse(course);

        Marks marks = new Marks();
        marks.setAssignmentMarks(List.of(100d,90d));
        marks.setFinalExamMarks(List.of(100d,90d));
        marks.setMidTermExamMarks(List.of(100d,90d));
        marks.setProjectMarks(List.of(100d,90d));
        marks.setQuizMarks(List.of(100d,90d));
        marks.setParticipation(100);

        Set<RankingTableModel> rankingTable = app.getRankingTable();

        RankingTableModel rankingTableModel = new RankingTableModel();
        rankingTableModel.setStudent(student);
        rankingTableModel.setWeightedTotalPercentage(studentGrades.getAggregate());
        rankingTable.add(rankingTableModel);


    }
}
