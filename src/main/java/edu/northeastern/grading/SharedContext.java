package edu.northeastern.grading;

import edu.northeastern.grading.model.Grade;
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
    private final List<Grade> gradeList = new ArrayList<>();

    private Set<RankingTableModel> rankingTable = new TreeSet<>(Comparator.reverseOrder());


    private SharedContext() {
        initGradeList();
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

    public void addToRankingTable(RankingTableModel rankingTableModel) {
        rankingTable.add(rankingTableModel);
    }

    private void initGradeList(){
        gradeList.add(new Grade("A",4F,"Outstanding Achievement"));
        gradeList.add(new Grade("A-",3.667F,null));
        gradeList.add(new Grade("B+",3.333F,null));
        gradeList.add(new Grade("B",3F,"Good Achievement"));
        gradeList.add(new Grade("B-",2.667F,null));
        gradeList.add(new Grade("C+",2.333F,null));
        gradeList.add(new Grade("C",2F,"Satisfactory Achievement"));
        gradeList.add(new Grade("C-",1.667F,null));
        gradeList.add(new Grade("F",0F,"Failure"));
        gradeList.add(new Grade("I",null,"Incomplete"));
        gradeList.add(new Grade("IP",null,"In Progress"));
        gradeList.add(new Grade("NE",null,"Not Enrolled"));
        gradeList.add(new Grade("L",null,"Audit (No Credit Given)"));
        gradeList.add(new Grade("W",null,"Course Withdrawal"));
    }

    public List<Grade> getGradeList(){
        return new ArrayList<>(gradeList);
    }

    public Grade getGrade(String letter){
        return gradeList.stream().filter(grade -> grade.getLetterGrade().equalsIgnoreCase(letter)).findAny().get();
    }
}
