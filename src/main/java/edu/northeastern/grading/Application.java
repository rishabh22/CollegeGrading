package edu.northeastern.grading;

import edu.northeastern.grading.model.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Set;

public class Application extends javafx.application.Application {
    private SharedContext app;

    @Override
    public void start(Stage stage) throws Exception {
        app = SharedContext.getInstance();
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
        Course.CourseBuilder courseBuilder = Course.getBuilder("Program Structures and Algorithms", GradeMode.ABSOLUTE_AUTO, true);

        courseBuilder.addCriteria(GradingCriteria.ASSIGNMENT, 3);
        Course course = courseBuilder.getInstance();

        Student student = new Student("001300579", "Rishabh Sood");
        app.addStudent(student);

        StudentGrades studentGrades = new StudentGrades();
        studentGrades.setStudent(student);
        studentGrades.setCourse(course);

        Marks marks = new Marks();
        marks.addMarks(GradingCriteria.ASSIGNMENT, 90, 100);
        marks.addMarks(GradingCriteria.ASSIGNMENT, 95, 100);

        studentGrades.setMarks(marks);


        Set<RankingTableModel> rankingTable = app.getRankingTable();

        RankingTableModel rankingTableModel = new RankingTableModel();
        rankingTableModel.setStudent(student);
        rankingTableModel.setWeightedTotalPercentage(studentGrades.getAggregate());
        app.addToRankingTable(rankingTableModel);


    }
}
