package edu.northeastern.grading.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Marks {
    private final List<Map<GradingCriteria, Double>> marks = new ArrayList<>();

    public List<Map<GradingCriteria, Double>> getMarks() {
        return new ArrayList<>(marks);
    }

    private double participation = 0;

    public Double getBestOf(List<Double> marksList, int consideredCount){
        List<Double> marksListCopy = new ArrayList<>(marksList);
        marksListCopy.sort(Collections.reverseOrder());
        return marksListCopy.stream().mapToDouble(Double::doubleValue).limit(consideredCount).average().getAsDouble();
    }


}
