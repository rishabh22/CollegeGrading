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
public class RankingTableModel {
    private Student student;
    private int rank;
    private double weightedTotalPercentage;

}
