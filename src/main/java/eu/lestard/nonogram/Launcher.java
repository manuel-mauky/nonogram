package eu.lestard.nonogram;

import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import eu.lestard.nonogram.puzzle.PuzzleView;
import eu.lestard.nonogram.puzzle.PuzzleViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String...args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ViewLoader viewLoader = new ViewLoader();

        final ViewTuple<PuzzleView, PuzzleViewModel> viewTuple = viewLoader.loadViewTuple(PuzzleView.class);

        primaryStage.setScene(new Scene(viewTuple.getView(), 700, 700));

        primaryStage.setResizable(false);

        primaryStage.show();

    }
}
