package eu.lestard.nonogram.core;

import eu.lestard.grid.Cell;
import org.junit.Before;
import org.junit.Test;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class GameInstanceTest {

    private GameInstance gameInstance;

    private Puzzle puzzle;

    @Before
    public void setup(){

        //  [x][ ][ ]
        //  [ ][x][x]
        //  [x][ ][ ]
        puzzle = new Puzzle();
        puzzle.setSize(3);

        puzzle.addPoint(0, 0);
        puzzle.addPoint(1, 1);
        puzzle.addPoint(2, 1);
        puzzle.addPoint(0, 2);

        gameInstance = new GameInstance(puzzle);
    }

    @Test
    public void testFinishedColumnsAndRows(){

        assertThat(gameInstance.finishedColumnsList()).isEmpty();
        assertThat(gameInstance.finishedRowsList()).isEmpty();

        assertThat(gameInstance.win()).isFalse();

        gameInstance.markWithSingleClick(cell(1, 0));
        gameInstance.markWithSingleClick(cell(2, 0));
        gameInstance.revealWithSingleClick(cell(0, 0));

        assertThat(gameInstance.finishedColumnsList()).isEmpty();
        assertThat(gameInstance.finishedRowsList()).containsOnly(0);
        assertThat(gameInstance.win()).isFalse();


        gameInstance.markWithSingleClick(cell(0, 1));
        gameInstance.revealWithSingleClick(cell(1, 1));
        gameInstance.revealWithSingleClick(cell(2, 1));
        assertThat(gameInstance.finishedColumnsList()).containsOnly(1,2);
        assertThat(gameInstance.finishedRowsList()).containsOnly(0, 1);
        assertThat(gameInstance.win()).isFalse();

        gameInstance.revealWithSingleClick(cell(0, 2));
        assertThat(gameInstance.finishedColumnsList()).containsOnly(0, 1, 2);
        assertThat(gameInstance.finishedRowsList()).containsOnly(0, 1, 2);
        assertThat(gameInstance.win()).isTrue();

    }

    private Cell<State> cell(int column, int row){
        return gameInstance.getGridModel().getCell(column,row);
    }
}

