package edu.northeastern.grading.controller;

import edu.northeastern.grading.SharedContext;
import edu.northeastern.grading.model.RankingTableModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class GradingController {
    private SharedContext app;

    @FXML
    private TableView<RankingTableModel> tblRanking;

    @FXML
    private TableColumn<RankingTableModel, Integer> colRank;

    @FXML
    private TableColumn<RankingTableModel, Double> colAggregate;

    @FXML
    private TableColumn<RankingTableModel, String> colStudentName;

    private ObservableList<RankingTableModel> rankingTableObservableList;

    @FXML
    private void initialize() {
        this.app = SharedContext.getInstance();

        this.rankingTableObservableList = FXCollections.observableList(new ArrayList<>(app.getRankingTable()));
        tblRanking.setItems(rankingTableObservableList);

        colRank.setCellFactory(col -> {
            TableCell<RankingTableModel, Integer> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex()+1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });

        mapTableColumns();
    }

    private void mapTableColumns() {
        colStudentName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStudent().getName()));
        colAggregate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getWeightedTotalPercentage()));
    }

   /* @FXML
    private void back() {
        app.goBack();
    }*/
}
