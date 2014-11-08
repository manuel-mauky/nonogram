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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.Optional;

public class MainView implements FxmlView<MainViewModel> {


    @FXML
    private HBox errorsBox;

    @FXML
    private SquareContainer center;

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
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(viewModel.sizeProperty().get(), viewModel.getSizeOptions());
        dialog.setTitle("New Game");
        dialog.setHeaderText("Start a new Game");
        dialog.setContentText("Choose the size:");

        Optional<Integer> result = dialog.showAndWait();
        result.ifPresent(size->{
            viewModel.sizeProperty().set(size);

            final ViewTuple<PuzzleView, PuzzleViewModel> viewTuple = FluentViewLoader.fxmlView(PuzzleView.class).load();

            final Parent view = viewTuple.getView();

            center.setMinSize(0,0);
            center.setPrefSize(0,0);

            if(view instanceof Region){
                center.setContent((Region) view);
            }
        });


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
