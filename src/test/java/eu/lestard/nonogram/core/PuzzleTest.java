package eu.lestard.nonogram.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PuzzleTest {


    @Test
    public void testValidateNumbers(){

        Puzzle puzzle = new Puzzle(10);


        assertThat(puzzle.validateNumbers()).isTrue();
        assertThat(puzzle.validateNumbers(1)).isTrue();
        assertThat(puzzle.validateNumbers(1,1)).isTrue();
        assertThat(puzzle.validateNumbers(1,1,1)).isTrue();
        assertThat(puzzle.validateNumbers(1,1,1,1)).isTrue();
        assertThat(puzzle.validateNumbers(1,1,1,1,1)).isTrue();

        assertThat(puzzle.validateNumbers(3)).isTrue();
        assertThat(puzzle.validateNumbers(3,3)).isTrue();
        assertThat(puzzle.validateNumbers(3,3,1)).isTrue();
        assertThat(puzzle.validateNumbers(3,3,2)).isTrue();

        assertThat(puzzle.validateNumbers(8)).isTrue();
        assertThat(puzzle.validateNumbers(9)).isTrue();
        assertThat(puzzle.validateNumbers(10)).isTrue();

        assertThat(puzzle.validateNumbers(1,1,1,1,1,1)).isFalse();
        assertThat(puzzle.validateNumbers(2,2,2,2)).isFalse();
        assertThat(puzzle.validateNumbers(3,3,3)).isFalse();
        assertThat(puzzle.validateNumbers(5,5)).isFalse();
        assertThat(puzzle.validateNumbers(11)).isFalse();
    }


}
