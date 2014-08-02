package eu.lestard.nonogram.puzzle;

import de.saxsys.jfx.mvvm.api.FxmlView;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PuzzleView implements FxmlView<PuzzleViewModel> {


    @FXML
    private Pane centerPane;

    @FXML
    private Pane overviewPane;

    @FXML
    private Pane topNumberPane;

    @FXML
    private Pane leftNumberPane;

    @FXML
    private AnchorPane rootPane;


    public void initialize(){

        final DoubleBinding oneThirdsWidth = rootPane.widthProperty().divide(3);
        final DoubleBinding oneThirdsHeight = rootPane.heightProperty().divide(3);

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

        AnchorPane.setTopAnchor(overviewPane, 0.0);
        AnchorPane.setLeftAnchor(overviewPane,0.0);

        AnchorPane.setTopAnchor(topNumberPane, 0.0);
        AnchorPane.setRightAnchor(topNumberPane, 0.0);

        AnchorPane.setBottomAnchor(leftNumberPane, 0.0);
        AnchorPane.setLeftAnchor(leftNumberPane, 0.0);


        AnchorPane.setBottomAnchor(centerPane,0.0);
        AnchorPane.setRightAnchor(centerPane,0.0);
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
