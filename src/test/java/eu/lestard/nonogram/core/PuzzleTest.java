package eu.lestard.nonogram.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class PuzzleTest {



    @Test
    public void testPuzzleGrid(){

        Puzzle puzzle = new Puzzle(4);

        assertThat(puzzle.getColumnNumbers(0)).isEmpty();
        assertThat(puzzle.getColumnNumbers(1)).isEmpty();
        assertThat(puzzle.getColumnNumbers(2)).isEmpty();
        assertThat(puzzle.getColumnNumbers(3)).isEmpty();

        assertThat(puzzle.getRowNumbers(0)).isEmpty();
        assertThat(puzzle.getRowNumbers(1)).isEmpty();
        assertThat(puzzle.getRowNumbers(2)).isEmpty();
        assertThat(puzzle.getRowNumbers(3)).isEmpty();



        puzzle.addPoint(1,1);

        assertThat(puzzle.getColumnNumbers(1)).containsOnly(1);
        assertThat(puzzle.getRowNumbers(1)).containsOnly(1);


        puzzle.addPoint(1, 2);

        assertThat(puzzle.getColumnNumbers(1)).containsOnly(2);
        assertThat(puzzle.getRowNumbers(1)).containsOnly(1);
        assertThat(puzzle.getRowNumbers(2)).containsOnly(1);


        puzzle.addPoint(3, 1);
        assertThat(puzzle.getRowNumbers(1)).containsOnly(1,1);


        puzzle.addPoint(0, 1);
        assertThat(puzzle.getRowNumbers(1)).containsOnly(2,1);



        assertThat(puzzle.isPoint(0,0)).isFalse();
        assertThat(puzzle.isPoint(2,2)).isFalse();
        assertThat(puzzle.isPoint(1,1)).isTrue();
        assertThat(puzzle.isPoint(1,2)).isTrue();
        assertThat(puzzle.isPoint(3,1)).isTrue();
        assertThat(puzzle.isPoint(0,1)).isTrue();


    }

}
