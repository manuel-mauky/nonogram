package eu.lestard.nonogram.core;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class GameManager {

    private int size = 10;

    private ReadOnlyObjectWrapper<GameInstance> gameInstance = new ReadOnlyObjectWrapper<>();

    public void newGame(){
        Puzzle puzzle = new Puzzle();
        puzzle.setSize(size);

        initRandomPuzzle(puzzle);

        gameInstance.setValue(new GameInstance(puzzle));
    }

    public ReadOnlyObjectProperty<GameInstance> gameInstanceProperty(){
        return gameInstance.getReadOnlyProperty();
    }

    public void setSize(int size){
        this.size = size;
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
