package eu.lestard.nonogram.puzzle;

import de.saxsys.jfx.mvvm.api.FxmlView;
import eu.lestard.grid.GridModel;
import eu.lestard.grid.GridView;
import eu.lestard.nonogram.core.State;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PuzzleView implements FxmlView<PuzzleViewModel> {

    private static final int COLUMNS = 20;
    private static final int ROWS = 20;



    @FXML
    private AnchorPane centerPane;

    @FXML
    private AnchorPane overviewPane;

    @FXML
    private AnchorPane topNumberPane;

    @FXML
    private AnchorPane leftNumberPane;

    @FXML
    private AnchorPane rootPane;


    public void initialize(){
        initLayout();

        initCenterGrid();

        initTopNumberGrid();

        initLeftNumberGrid();

        initOverviewGrid();
    }

    private void initOverviewGrid() {
        GridModel<State> overviewGridModel = new GridModel<>();
        overviewGridModel.setNumberOfColumns(COLUMNS);
        overviewGridModel.setNumberOfRows(ROWS);

        GridView<State> overviewGridView = new GridView<>();
        overviewGridView.setGridModel(overviewGridModel);

        overviewPane.getChildren().add(overviewGridView);

        initAnchor(overviewGridView);
    }


    private void initLeftNumberGrid() {
        GridModel<State> leftNumberGridModel = new GridModel<>();
        leftNumberGridModel.setNumberOfColumns(COLUMNS/2);
        leftNumberGridModel.setNumberOfRows(ROWS);

        GridView<State> leftNumberGridView = new GridView<>();
        leftNumberGridView.setGridModel(leftNumberGridModel);
        leftNumberPane.getChildren().add(leftNumberGridView);

        initAnchor(leftNumberGridView);
    }

    private void initTopNumberGrid() {
        GridModel<State> topNumberGridModel = new GridModel<>();
        topNumberGridModel.setNumberOfColumns(COLUMNS);
        topNumberGridModel.setNumberOfRows(ROWS/2);

        GridView<State> topNumberGridView = new GridView<>();
        topNumberGridView.setGridModel(topNumberGridModel);
        topNumberPane.getChildren().add(topNumberGridView);

        initAnchor(topNumberGridView);
    }

    private void initCenterGrid() {
        GridModel<State> centerGridModel = new GridModel<>();

        centerGridModel.setNumberOfColumns(COLUMNS);
        centerGridModel.setNumberOfRows(ROWS);

        GridView<State> centerGridView = new GridView<>();
        centerGridView.setGridModel(centerGridModel);

        centerPane.getChildren().add(centerGridView);
        initAnchor(centerGridView);
    }

    private void initAnchor(Node node){
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }



    private void initLayout() {
        rootPane.setStyle("-fx-background-color:black");

        double anchorMargin = 1;

        final DoubleBinding oneThirdsWidth = rootPane.widthProperty().subtract(anchorMargin*4).divide(3);
        final DoubleBinding oneThirdsHeight = rootPane.heightProperty().subtract(anchorMargin*4).divide(3);

        final DoubleBinding twoThirdsWidth = oneThirdsWidth.multiply(2);
        final DoubleBinding twoThirdsHeight = oneThirdsHeight.multiply(2);

        bindWidth(overviewPane, oneThirdsWidth);
        bindHeight(overviewPane, oneThirdsHeight);

        bindWidth(topNumberPane, twoThirdsWidth);
        bindHeight(topNumberPane, oneThirdsHeight);

        bindWidth(leftNumberPane, oneThirdsWidth);
        bindHeight(leftNumberPane, twoThirdsHeight);

        bindWidth(centerPane, twoThirdsWidth);
        bindHeight(centerPane, twoThirdsHeight);



        AnchorPane.setTopAnchor(overviewPane, anchorMargin);
        AnchorPane.setLeftAnchor(overviewPane, anchorMargin);

        AnchorPane.setTopAnchor(topNumberPane, anchorMargin);
        AnchorPane.setRightAnchor(topNumberPane, anchorMargin);

        AnchorPane.setBottomAnchor(leftNumberPane, anchorMargin);
        AnchorPane.setLeftAnchor(leftNumberPane, anchorMargin);

        AnchorPane.setBottomAnchor(centerPane,anchorMargin);
        AnchorPane.setRightAnchor(centerPane,anchorMargin);
    }

    private void bindWidth(Pane pane, ObservableDoubleValue width){
        pane.minWidthProperty().bind(width);
        pane.maxWidthProperty().bind(width);
    }

    private void bindHeight(Pane pane, ObservableDoubleValue height){
        pane.minHeightProperty().bind(height);
        pane.maxHeightProperty().bind(height);
    }

}
