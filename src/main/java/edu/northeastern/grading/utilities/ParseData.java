package edu.northeastern.grading.utilities;

import edu.northeastern.grading.SharedContext;
import edu.northeastern.grading.model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ParseData {
    private SharedContext app = SharedContext.getInstance();

    public void parse(Course course) throws IOException {
        String excelFilePath = "C:\\Users\\soodr\\Desktop\\testgrades.xlsx";

        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        List<String> headerNames = new ArrayList<>();
        Row currentRow = iterator.next();
        Iterator<Cell> cellIterator = currentRow.iterator();

        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            headerNames.add(currentCell.getStringCellValue());
        }

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            cellIterator = nextRow.cellIterator();

            Student student = new Student();
            Marks marks = new Marks();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                String curColHeader = headerNames.get(columnIndex).toLowerCase();

                if (curColHeader.contains("name")) {
                    if (curColHeader.contains("last")) {
                        student.setLastName(cell.getStringCellValue());
                    } else if (curColHeader.contains("first")) {
                        student.setFirstName(cell.getStringCellValue());
                    }
                } else if (curColHeader.contains("nuid")){
                    student.setNuid(cell.getStringCellValue());
                } else {
                    Arrays.stream(GradingCriteria.values())
                            .filter(gradingCriteria -> curColHeader.contains(gradingCriteria.toString().toLowerCase()))
                            .findAny().ifPresent(gradingCriteria -> marks.addMarks(gradingCriteria, cell.getNumericCellValue(),Double.parseDouble(curColHeader.split("/")[1].trim())));
                }
                /*switch (columnIndex) {
                    case 0 -> student.setLastName(cell.getStringCellValue());
                    case 1 -> student.setFirstName(cell.getStringCellValue());
                    case 2 -> student.setNuid(cell.getStringCellValue());
                    default -> {

                    }
                }*/
            }
            app.addStudent(student);
            StudentGrades studentGrades = new StudentGrades();
            studentGrades.setStudent(student);
            studentGrades.setMarks(marks);
            studentGrades.setCourse(course);
            app.addStudentGrade(studentGrades);
        }

        workbook.close();
        inputStream.close();
    }
}
