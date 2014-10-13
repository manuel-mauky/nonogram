package eu.lestard.nonogram;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import eu.lestard.easydi.EasyDI;
import eu.lestard.nonogram.core.Puzzle;
import eu.lestard.nonogram.main.MainView;
import eu.lestard.nonogram.main.MainViewModel;
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
        EasyDI easyDI = new EasyDI();
        MvvmFX.setCustomDependencyInjector(type -> easyDI.getInstance(type));


        Puzzle puzzle = easyDI.getInstance(Puzzle.class);

        puzzle.setSize(10);

        initRandomPuzzle(puzzle);

        final ViewTuple<MainView, MainViewModel> viewTuple = FluentViewLoader.fxmlView(MainView.class).load();

        primaryStage.setScene(new Scene(viewTuple.getView(), 600, 600));

        primaryStage.show();
    }


    private void initRandomPuzzle(Puzzle puzzle){
        int size = puzzle.getSize();

        Random rand = new Random();

        for(int i=0 ; i<(size*size) ; i++){
            int column = rand.nextInt(size);
            int row = rand.nextInt(size);

            puzzle.addPoint(column, row);
        }
    }
}
