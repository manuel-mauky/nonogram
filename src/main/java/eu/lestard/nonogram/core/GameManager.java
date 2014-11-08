package eu.lestard.nonogram.core;

import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class GameManager {

    private int size = 10;

    public GameInstance newGame(){
        Puzzle puzzle = new Puzzle();
        puzzle.setSize(size);

        initRandomPuzzle(puzzle);

        GameInstance gameInstance = new GameInstance(puzzle);

        return gameInstance;
    }

    public void setSize(int size){
        System.out.println("setSize(" + size + ")");
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
