package eu.lestard.nonogram.core;

import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class GameManager {


    public GameInstance newGame(int size){

        Puzzle puzzle = new Puzzle();
        puzzle.setSize(10);

        initRandomPuzzle(puzzle);

        GameInstance gameInstance = new GameInstance(puzzle);

        return gameInstance;
    }

    public GameInstance newGame(){
        return newGame(10);
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
