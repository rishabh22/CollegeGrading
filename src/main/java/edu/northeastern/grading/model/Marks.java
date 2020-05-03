package edu.northeastern.grading.model;

import java.util.*;

public class Marks {
    private final Map<GradingCriteria, List<AbstractMap.SimpleEntry<Double, Double>>> marksMap = new HashMap<>();

    public Map<GradingCriteria, List<AbstractMap.SimpleEntry<Double, Double>>> getMarks() {
        return new HashMap<>(marksMap);
    }

    public void addMarks(GradingCriteria gradingCriteria, double marks, double outOf) {
        /*if (gradingCriteria.equals(GradingCriteria.PARTICIPATION)) {
            marksMap.put(gradingCriteria, Collections.singletonList(marks));
            return;
        }*/
        if (marksMap.containsKey(gradingCriteria)) {
            marksMap.get(gradingCriteria).add(new AbstractMap.SimpleEntry<>(marks, outOf));
        } else {
            List<AbstractMap.SimpleEntry<Double, Double>> marksList = new ArrayList<>();
            marksList.add(new AbstractMap.SimpleEntry<>(marks, outOf));
            marksMap.put(gradingCriteria, marksList);
        }
    }

    public OptionalDouble getBestOf(GradingCriteria gradingCriteria, int consideredCount) {
        return marksMap.get(gradingCriteria).stream()
                .mapToDouble(item->item.getKey()/item.getValue()*100).boxed()
                .sorted(Comparator.reverseOrder())
                .limit(consideredCount == -1 ? marksMap.get(gradingCriteria).size() : consideredCount)
                .mapToDouble(Double::doubleValue).average();
    }


}
