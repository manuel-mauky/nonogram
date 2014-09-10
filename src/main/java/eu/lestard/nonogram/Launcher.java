package eu.lestard.nonogram;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import eu.lestard.nonogram.core.GameInstance;
import eu.lestard.nonogram.core.Puzzle;
import eu.lestard.nonogram.puzzle.PuzzleView;
import eu.lestard.nonogram.puzzle.PuzzleViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;

public class Launcher extends Application {

    public static void main(String...args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        final ViewTuple<PuzzleView, PuzzleViewModel> viewTuple = FluentViewLoader.fxmlView(PuzzleView.class).load();


        Puzzle puzzle = createRandomPuzzle(10);

        viewTuple.getViewModel().init(puzzle, new GameInstance(puzzle));

        primaryStage.setScene(new Scene(viewTuple.getView()));

        primaryStage.setResizable(false);

        primaryStage.show();
    }


    private Puzzle createRandomPuzzle(int size){

        Puzzle puzzle = new Puzzle(size);

        Random rand = new Random();

        for(int i=0 ; i<(size*size) ; i++){
            int column = rand.nextInt(size);
            int row = rand.nextInt(size);

            puzzle.addPoint(column, row);
        }

        return puzzle;
    }
}
