package edu.northeastern.grading.model;

import lombok.Getter;
import lombok.Setter;


/*
 * 1. Seasonal Weightage If less than 2 ma
 * 2. Overall Weightage
 * 3.
 * */
@Getter
@Setter
public class RankingTableModel implements Comparable<RankingTableModel> {
    private Student student;
    //    private int rank;
    private double weightedTotalPercentage;

    @Override
    public int compareTo(RankingTableModel o) {
        return Double.compare(o.weightedTotalPercentage, this.weightedTotalPercentage);
    }
}
