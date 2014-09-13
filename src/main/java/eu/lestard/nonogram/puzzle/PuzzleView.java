package eu.lestard.nonogram.puzzle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.lestard.grid.GridView;
import eu.lestard.nonogram.core.State;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PuzzleView implements FxmlView<PuzzleViewModel> {

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

    @FXML
    private HBox errorsBox;

    @InjectViewModel
    private PuzzleViewModel viewModel;


    public void initialize(){
        initLayout();

        viewModel.centerGridModelProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null){
                initCenterGrid();
            }
        });

        initTopNumberGrid();

        initLeftNumberGrid();

        viewModel.overviewGridModelProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                initOverviewGrid();
            }
        });


        viewModel.currentErrorsProperty().addListener(observable-> updateErrorsBox());
        viewModel.maxErrorsProperty().addListener(observable -> updateErrorsBox());

        updateErrorsBox();
    }

    private void initOverviewGrid() {
        GridView<State> overviewGridView = new GridView<>();
        overviewGridView.setGridModel(viewModel.overviewGridModelProperty().get());

        overviewPane.getChildren().add(overviewGridView);

        overviewGridView.addColorMapping(State.FILLED, Color.BLACK);

        initAnchor(overviewGridView);
    }


    private void initLeftNumberGrid() {
        GridView<Integer> leftNumberGridView = new GridView<>();
        leftNumberGridView.setGridModel(viewModel.getLeftNumberGridModel());
        leftNumberPane.getChildren().add(leftNumberGridView);

        initNumberGridMapping(leftNumberGridView);

        initAnchor(leftNumberGridView);
    }

    private void initNumberGridMapping(GridView<Integer> gridView){
        viewModel.sizeProperty().addListener((obs,oldV, newV) ->{
            for(int i=1 ; i<=newV.intValue() ; i++){
                final String labelText = Integer.toString(i);
                gridView.addNodeMapping(i, cell-> new Label(labelText));
            }
        });
    }

    private void initTopNumberGrid() {
        GridView<Integer> topNumberGridView = new GridView<>();
        topNumberGridView.setGridModel(viewModel.getTopNumberGridModel());
        topNumberPane.getChildren().add(topNumberGridView);

        initNumberGridMapping(topNumberGridView);
        initAnchor(topNumberGridView);
    }

    private void initCenterGrid() {
        GridView<State> centerGridView = new GridView<>();
        centerGridView.setGridModel(viewModel.centerGridModelProperty().get());

        centerGridView.addColorMapping(State.EMPTY, Color.WHITE);

        centerGridView.addColorMapping(State.FILLED, Color.BLACK);

        centerGridView.addNodeMapping(State.ERROR, cell -> new Cross(Color.RED));

        centerGridView.addNodeMapping(State.MARKED, cell -> new Cross());

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


        IntegerProperty numberOfCenterColumns = new SimpleIntegerProperty();
        IntegerProperty numberOfCenterRows = new SimpleIntegerProperty();
        viewModel.centerGridModelProperty().addListener((obs, oldV, newV)->{
            if(newV != null){
                numberOfCenterColumns.unbind();
                numberOfCenterColumns.bind(newV.numberOfColumns());

                numberOfCenterRows.unbind();
                numberOfCenterRows.bind(newV.numberOfRows());
            }
        });


        final IntegerProperty numberOfLeftColumns = viewModel.getLeftNumberGridModel().numberOfColumns();
        final NumberBinding numberOfColumns = numberOfCenterColumns.add(numberOfLeftColumns);
        final DoubleBinding widthOfEveryColumn = rootPane.widthProperty().subtract(anchorMargin * 4).divide(numberOfColumns);

        final IntegerProperty numberOfTopRows = viewModel.getTopNumberGridModel().numberOfRows();
        final NumberBinding numberOfRows = numberOfCenterColumns.add(numberOfTopRows);
        final DoubleBinding heightOfEveryRow = rootPane.heightProperty().subtract(anchorMargin * 4).divide(numberOfRows);


        bindWidth(centerPane, widthOfEveryColumn.multiply(numberOfCenterColumns));
        bindHeight(centerPane, heightOfEveryRow.multiply(numberOfCenterRows));

        bindWidth(leftNumberPane, widthOfEveryColumn.multiply(numberOfLeftColumns));
        bindHeight(leftNumberPane, heightOfEveryRow.multiply(numberOfCenterRows));

        bindWidth(overviewPane, widthOfEveryColumn.multiply(numberOfLeftColumns));
        bindHeight(overviewPane, heightOfEveryRow.multiply(numberOfTopRows));

        bindWidth(topNumberPane, widthOfEveryColumn.multiply(numberOfCenterColumns));
        bindHeight(topNumberPane, heightOfEveryRow.multiply(numberOfTopRows));


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

    private void updateErrorsBox(){
        errorsBox.getChildren().clear();

        final int currentErrors = viewModel.currentErrorsProperty().get();

        for (int i=0 ; i<currentErrors ; i++){
            errorsBox.getChildren().add(new ErrorBox(true));
        }


        final int maxErrors = viewModel.maxErrorsProperty().get();

        for (int i = 0; i < (maxErrors - currentErrors); i++) {
            errorsBox.getChildren().add(new ErrorBox(false));
        }

    }
}
