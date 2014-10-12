package eu.lestard.nonogram.puzzle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.lestard.grid.Cell;
import eu.lestard.grid.GridView;
import eu.lestard.nonogram.core.State;
import javafx.application.Platform;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.controlsfx.dialog.Dialogs;

import java.util.List;
import java.util.function.Function;

public class PuzzleView implements FxmlView<PuzzleViewModel> {

    private static final int GUIDELINES = 5;

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


    @InjectViewModel
    private PuzzleViewModel viewModel;

    public void initialize(){
        initLayout();

        initTopNumberGrid();
        initLeftNumberGrid();

        initCenterGrid();
        initOverviewGrid();

        viewModel.gameFinishedProperty().addListener((obs, oldV, newV) -> {
            if (newV) {
                Platform.runLater(() -> Dialogs.create()
                    .title("Win")
                    .message("You have successfully finished the game!")
                    .showInformation());
            }
        });

        viewModel.gameOverProperty().addListener((obs,oldV, newV)->{
            if(newV){
                Platform.runLater(() -> Dialogs.create()
                    .title("GameOver")
                    .message("You have lost the game!")
                    .showInformation());
            }
        });


    }

    private void initOverviewGrid() {
        GridView<State> overviewGridView = new GridView<>();
        overviewGridView.setGridModel(viewModel.getOverviewGridModel());

        overviewPane.getChildren().add(overviewGridView);

        overviewGridView.addColorMapping(State.FILLED, Color.BLACK);

        initAnchor(overviewGridView);
    }


    private void initLeftNumberGrid() {
        GridView<Integer> leftNumberGridView = new GridView<>();
        leftNumberGridView.setGridModel(viewModel.getLeftNumberGridModel());
        leftNumberPane.getChildren().add(leftNumberGridView);



        leftNumberGridView.horizontalGuidelineUnitProperty().setValue(GUIDELINES);
        leftNumberGridView.guidelineStrokeWidth().set(1);
        leftNumberGridView.guidelineColorProperty().set(Color.DIMGREY);


        initNumberGridMapping(leftNumberGridView);

        initAnchor(leftNumberGridView);
        initFinishedStyleBinding(viewModel.finishedRows(), leftNumberGridView, viewModel.getLeftNumberGridModel()::getCellsOfRow);
    }

    private void initTopNumberGrid() {
        GridView<Integer> topNumberGridView = new GridView<>();
        topNumberGridView.setGridModel(viewModel.getTopNumberGridModel());
        topNumberPane.getChildren().add(topNumberGridView);


        topNumberGridView.verticalGuidelineUnitProperty().setValue(GUIDELINES);
        topNumberGridView.guidelineStrokeWidth().set(1);
        topNumberGridView.guidelineColorProperty().set(Color.DIMGREY);

        initNumberGridMapping(topNumberGridView);
        initAnchor(topNumberGridView);
        initFinishedStyleBinding(viewModel.finishedColumns(), topNumberGridView, viewModel.getTopNumberGridModel()::getCellsOfColumn);
    }


    private void initNumberGridMapping(GridView<Integer> gridView){
        final int size = viewModel.getSize();

        int fontSize = calculateFontSize(size);
        for(int i=1 ; i<=size ; i++){
            final String labelText = Integer.toString(i);
            gridView.addNodeMapping(i, cell-> {
                final Label label = new Label(labelText);
                label.setStyle("-fx-font-size:" + fontSize);
                return label;
            });
        }
    }

    private int calculateFontSize(int size){
        if(size <=10){
            return 20;
        }

        if(size <= 15){
            return 13;
        }

        if(size <= 20){
            return 11;
        }

        return 9;
    }


    private void initFinishedStyleBinding(ObservableList<Integer> finishedBlocks, GridView<Integer> gridView, Function<Integer, List<Cell<Integer>>> func) {
        finishedBlocks.addListener((ListChangeListener<Integer>) c -> {
            c.next();

            c.getAddedSubList().forEach(i -> {
                func.apply(i).forEach(cell -> {
                    final Pane cellPane = gridView.getCellPane(cell);
                    cellPane.getChildren().forEach(child -> {
                        child.setStyle(child.getStyle() + ";-fx-font-weight:bold");
                    });
                });
            });
        });
    }


    private void initCenterGrid() {
        GridView<State> centerGridView = new GridView<>();
        centerGridView.setGridModel(viewModel.getCenterGridModel());

        centerGridView.horizontalGuidelineUnitProperty().setValue(GUIDELINES);
        centerGridView.verticalGuidelineUnitProperty().setValue(GUIDELINES);
        centerGridView.guidelineStrokeWidth().set(1);
        centerGridView.guidelineColorProperty().set(Color.DIMGREY);

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
}
