package edu.northeastern.grading;

import edu.northeastern.grading.model.RankingTableModel;
import edu.northeastern.grading.model.Student;
import edu.northeastern.grading.model.StudentGrades;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class SharedContext {
    private static volatile SharedContext sharedContext;

    @Setter
    @Getter
    private Stage currentStage;

    private final List<Student> studentList = new ArrayList<>();
    private final List<StudentGrades> studentGradesList = new ArrayList<>();

    private Set<RankingTableModel> rankingTable = new TreeSet<>(Comparator.comparingDouble(RankingTableModel::getWeightedTotalPercentage).reversed());


    private SharedContext() {
    }

    public synchronized static SharedContext getInstance() {
        if (sharedContext == null) {
            sharedContext = new SharedContext();
        }

        return sharedContext;
    }

    public List<Student> getStudentList() {
        return new ArrayList<>(studentList);
    }

    public void addStudent(Student student) {
        if (!studentList.contains(student)) {
            studentList.add(student);
        }
    }

    public Set<RankingTableModel> getRankingTable() {
        return new TreeSet<>(rankingTable);
    }
}
