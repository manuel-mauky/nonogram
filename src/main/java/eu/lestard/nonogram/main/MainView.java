package eu.lestard.nonogram.main;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import eu.lestard.nonogram.puzzle.ErrorBox;
import eu.lestard.nonogram.puzzle.PuzzleView;
import eu.lestard.nonogram.puzzle.PuzzleViewModel;
import eu.lestard.nonogram.util.SquareContainer;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MainView implements FxmlView<MainViewModel> {


    @FXML
    private HBox errorsBox;

    @FXML
    private BorderPane borderPane;

    @InjectViewModel
    private MainViewModel viewModel;

    public void initialize(){
        newGame();

        viewModel.currentErrorsProperty().addListener(observable -> updateErrorsBox());
        viewModel.maxErrorsProperty().addListener(observable -> updateErrorsBox());

        updateErrorsBox();
    }

    @FXML
    public void newGame(){
        final ViewTuple<PuzzleView, PuzzleViewModel> viewTuple = FluentViewLoader.fxmlView(PuzzleView.class).load();

        final Parent view = viewTuple.getView();
        if(view instanceof Pane){
            borderPane.setCenter(new SquareContainer((Pane)view));
        }
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
