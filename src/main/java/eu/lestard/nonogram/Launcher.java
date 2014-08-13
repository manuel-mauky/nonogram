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


        Puzzle puzzle = new Puzzle(10);

        puzzle.addHorizontalBlock();
        puzzle.addHorizontalBlock(1,4,3);
        puzzle.addHorizontalBlock(3,3);
        puzzle.addHorizontalBlock();
        puzzle.addHorizontalBlock(1,4,3);
        puzzle.addHorizontalBlock(7);
        puzzle.addHorizontalBlock(1,1,1,1,1);
        puzzle.addHorizontalBlock(1,1,1,1);
        puzzle.addHorizontalBlock();
        puzzle.addHorizontalBlock(7);

        puzzle.addVerticalBlock(4,3);
        puzzle.addVerticalBlock(4,3);
        puzzle.addVerticalBlock(4,3);
        puzzle.addVerticalBlock(4,3);
        puzzle.addVerticalBlock(4,3);
        puzzle.addVerticalBlock(4,3);
        puzzle.addVerticalBlock(1,1,1);
        puzzle.addVerticalBlock(1,1,1);
        puzzle.addVerticalBlock(1,1,1);
        puzzle.addVerticalBlock(1,1,1);

        viewTuple.getViewModel().init(puzzle);

        primaryStage.setScene(new Scene(viewTuple.getView(), 700, 700));

        primaryStage.setResizable(false);

        primaryStage.show();

    }
}
