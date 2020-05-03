package edu.northeastern.grading.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String nuid;
    private String firstName;
    private String lastName;

    public String getName(){
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(nuid, student.nuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nuid);
    }
}
