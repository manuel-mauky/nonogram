package eu.lestard.nonogram;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import eu.lestard.nonogram.core.Puzzle;
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


        final ViewTuple<PuzzleView, PuzzleViewModel> viewTuple = FluentViewLoader.fxmlView(PuzzleView.class).load();


        Puzzle puzzle = new Puzzle(6);

        puzzle.addPoint(0, 0);
        puzzle.addPoint(0, 1);
        puzzle.addPoint(0, 3);
        puzzle.addPoint(1, 1);
        puzzle.addPoint(4, 2);


        viewTuple.getViewModel().init(puzzle);

        primaryStage.setScene(new Scene(viewTuple.getView(), 700, 700));

        primaryStage.setResizable(false);

        primaryStage.show();

    }
}
