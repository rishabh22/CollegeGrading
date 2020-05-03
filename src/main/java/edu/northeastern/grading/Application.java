package edu.northeastern.grading;

import edu.northeastern.grading.model.*;
import edu.northeastern.grading.utilities.ParseData;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

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
        Course.CourseBuilder courseBuilder = Course.getBuilder("AED", GradeMode.ABSOLUTE_AUTO, false);
        courseBuilder.setAutoAbsoluteGradeInterval(5);
        courseBuilder.addCriteria(GradingCriteria.ASSIGNMENT, 5);
        courseBuilder.addCriteria(GradingCriteria.QUIZ, 4);
        courseBuilder.addCriteria(GradingCriteria.PROJECT, 1);
        courseBuilder.addCriteria(GradingCriteria.ATTENDANCE, 1);

        courseBuilder.addWeightage(GradingCriteria.ASSIGNMENT,25);
        courseBuilder.addWeightage(GradingCriteria.QUIZ,25);
        courseBuilder.addWeightage(GradingCriteria.PROJECT,45);
        courseBuilder.addWeightage(GradingCriteria.ATTENDANCE,5);
        Course course = courseBuilder.getInstance();

        ParseData parseData = new ParseData();
        try{
            parseData.parse(course);
        } catch (Exception e){
            //TODO Return could not parse data
            e.printStackTrace();
        }

        app.getStudentGradesList().forEach(studentGrades -> {
            RankingTableModel rankingTableModel = new RankingTableModel();
            rankingTableModel.setStudent(studentGrades.getStudent());
            rankingTableModel.setWeightedTotalPercentage(studentGrades.getAggregate());

            if(course.getGradingMode().equals(GradeMode.ABSOLUTE_AUTO)){
                for (Map.Entry<Grade, Double> entry : course.getGradeMarksMap().entrySet()) {
                    if (entry.getValue() != null && rankingTableModel.getWeightedTotalPercentage() > entry.getValue().doubleValue()) {
                        rankingTableModel.setAssignedGrade(entry.getKey());
                        break;
                    }
                }
            }

            app.addToRankingTable(rankingTableModel);
        });




    }
}
