package eu.lestard.nonogram.puzzle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.lestard.grid.Cell;
import eu.lestard.grid.GridView;
import eu.lestard.nonogram.core.State;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Function;

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
        initFinishedStyleBinding(viewModel.finishedRows(), leftNumberGridView, viewModel.getLeftNumberGridModel()::getCellsOfRow);
    }

    private void initTopNumberGrid() {
        GridView<Integer> topNumberGridView = new GridView<>();
        topNumberGridView.setGridModel(viewModel.getTopNumberGridModel());
        topNumberPane.getChildren().add(topNumberGridView);

        initNumberGridMapping(topNumberGridView);
        initAnchor(topNumberGridView);
        initFinishedStyleBinding(viewModel.finishedColumns(), topNumberGridView, viewModel.getTopNumberGridModel()::getCellsOfColumn);
    }


    private void initNumberGridMapping(GridView<Integer> gridView){
        viewModel.sizeProperty().addListener((obs,oldV, newV) ->{
            for(int i=1 ; i<=newV.intValue() ; i++){
                final String labelText = Integer.toString(i);
                gridView.addNodeMapping(i, cell-> new Label(labelText));
            }
        });
    }


    private void initFinishedStyleBinding(ObservableList<Integer> finishedBlocks, GridView<Integer> gridView, Function<Integer, List<Cell<Integer>>> func) {
        finishedBlocks.addListener((ListChangeListener<Integer>) c -> {
            c.next();

            c.getAddedSubList().forEach(i -> {
                func.apply(i).forEach(cell -> {
                    final Pane cellPane = gridView.getCellPane(cell);
                    cellPane.getChildren().forEach(child -> {
                        child.setStyle("-fx-font-weight:bold");
                    });
                });
            });
        });
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

        viewModel.rootHeightProperty().bind(rootPane.heightProperty().subtract(4 * anchorMargin));
        viewModel.rootWidthProperty().bind(rootPane.widthProperty().subtract(4 * anchorMargin));

        bindWidth(centerPane, viewModel.centerWidthProperty());
        bindHeight(centerPane, viewModel.centerHeightProperty());

        bindWidth(leftNumberPane, viewModel.overviewWidth());
        bindHeight(leftNumberPane, viewModel.centerHeightProperty());

        bindWidth(overviewPane, viewModel.overviewWidth());
        bindHeight(overviewPane, viewModel.overviewHeight());

        bindWidth(topNumberPane, viewModel.centerWidthProperty());
        bindHeight(topNumberPane, viewModel.overviewHeight());


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
