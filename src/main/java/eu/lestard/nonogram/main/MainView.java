package eu.lestard.nonogram.main;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.lestard.nonogram.puzzle.ErrorBox;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class MainView implements FxmlView<MainViewModel> {


    @FXML
    private HBox errorsBox;

    @InjectViewModel
    private MainViewModel viewModel;

    public void initialize(){
        viewModel.currentErrorsProperty().addListener(observable-> updateErrorsBox());
        viewModel.maxErrorsProperty().addListener(observable -> updateErrorsBox());

        updateErrorsBox();
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
