package eu.lestard.nonogram.puzzle;

import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.nonogram.core.GameInstance;
import eu.lestard.nonogram.core.Puzzle;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class PuzzleViewModelTest {

    private PuzzleViewModel viewModel;

    private Puzzle puzzle3;
    private Puzzle puzzle4;
    private Puzzle puzzle5;

    private GameInstance gameInstance;

    @Before
    public void setup() {


        //        [ ][x][x]
        //        [ ][x][ ]
        //        [ ][x][x]
        puzzle3 = new Puzzle();
        puzzle3.setSize(3);
        puzzle3.addPoint(1, 0);
        puzzle3.addPoint(2, 0);
        puzzle3.addPoint(1, 1);
        puzzle3.addPoint(1, 2);
        puzzle3.addPoint(2, 2);

        //        [ ][x][ ][x]
        //        [x][x][x][ ]
        //        [ ][x][ ][ ]
        //        [x][x][ ][x]
        puzzle4 = new Puzzle();
        puzzle4.setSize(4);
        puzzle4.addPoint(0, 1);
        puzzle4.addPoint(0, 3);
        puzzle4.addPoint(1, 0);
        puzzle4.addPoint(1, 1);
        puzzle4.addPoint(1, 2);
        puzzle4.addPoint(1, 3);
        puzzle4.addPoint(2, 1);
        puzzle4.addPoint(3, 0);
        puzzle4.addPoint(3, 3);

        //        [x][x][ ][ ][x]
        //        [ ][x][ ][ ][ ]
        //        [x][x][ ][ ][ ]
        //        [ ][x][ ][x][ ]
        //        [x][x][ ][ ][ ]
        puzzle5 = new Puzzle();
        puzzle5.setSize(5);
        puzzle5.addPoint(0, 0);
        puzzle5.addPoint(0, 2);
        puzzle5.addPoint(0, 4);
        puzzle5.addPoint(1, 0);
        puzzle5.addPoint(1, 1);
        puzzle5.addPoint(1, 2);
        puzzle5.addPoint(1, 3);
        puzzle5.addPoint(1, 4);
        puzzle5.addPoint(4, 0);
        puzzle5.addPoint(3, 3);


    }


    @Test
    public void testNumberGridModelsForPuzzleWithSizeOf3() {
        gameInstance = new GameInstance(puzzle3);

        viewModel = new PuzzleViewModel(puzzle3, gameInstance);

        final GridModel<Integer> left = viewModel.getLeftNumberGridModel();

        assertThat(left.getNumberOfColumns()).isEqualTo(2);
        assertThat(left.getNumberOfRows()).isEqualTo(3);

        assertThat(getCellValues(left.getCellsOfRow(0))).containsExactly(0, 2);
        assertThat(getCellValues(left.getCellsOfRow(1))).containsExactly(0, 1);
        assertThat(getCellValues(left.getCellsOfRow(2))).containsExactly(0, 2);

        final GridModel<Integer> top = viewModel.getTopNumberGridModel();

        assertThat(top.getNumberOfColumns()).isEqualTo(3);
        assertThat(top.getNumberOfRows()).isEqualTo(2);

        assertThat(getCellValues(top.getCellsOfColumn(0))).containsExactly(0, 0);
        assertThat(getCellValues(top.getCellsOfColumn(1))).containsExactly(0, 3);
        assertThat(getCellValues(top.getCellsOfColumn(2))).containsExactly(1, 1);
    }

    @Test
    public void testNumberGridModelsForPuzzleWithSizeOf4() {
        gameInstance = new GameInstance(puzzle4);

        viewModel = new PuzzleViewModel(puzzle4, gameInstance);

        final GridModel<Integer> left = viewModel.getLeftNumberGridModel();

        assertThat(left.getNumberOfColumns()).isEqualTo(2);
        assertThat(left.getNumberOfRows()).isEqualTo(4);

        assertThat(getCellValues(left.getCellsOfRow(0))).containsExactly(1, 1);
        assertThat(getCellValues(left.getCellsOfRow(1))).containsExactly(0, 3);
        assertThat(getCellValues(left.getCellsOfRow(2))).containsExactly(0, 1);
        assertThat(getCellValues(left.getCellsOfRow(3))).containsExactly(2, 1);

        final GridModel<Integer> top = viewModel.getTopNumberGridModel();

        assertThat(top.getNumberOfColumns()).isEqualTo(4);
        assertThat(top.getNumberOfRows()).isEqualTo(2);

        assertThat(getCellValues(top.getCellsOfColumn(0))).containsExactly(1, 1);
        assertThat(getCellValues(top.getCellsOfColumn(1))).containsExactly(0, 4);
        assertThat(getCellValues(top.getCellsOfColumn(2))).containsExactly(0, 1);
        assertThat(getCellValues(top.getCellsOfColumn(3))).containsExactly(1, 1);
    }


    @Test
    public void testNumberGridModelsForPuzzleWithSizeOf5() {
        gameInstance = new GameInstance(puzzle5);

        viewModel = new PuzzleViewModel(puzzle5, gameInstance);

        final GridModel<Integer> left = viewModel.getLeftNumberGridModel();
        assertThat(left.getNumberOfColumns()).isEqualTo(3);
        assertThat(left.getNumberOfRows()).isEqualTo(5);

        assertThat(getCellValues(left.getCellsOfRow(0))).containsExactly(0, 2, 1);
        assertThat(getCellValues(left.getCellsOfRow(1))).containsExactly(0, 0, 1);
        assertThat(getCellValues(left.getCellsOfRow(2))).containsExactly(0, 0, 2);
        assertThat(getCellValues(left.getCellsOfRow(3))).containsExactly(0, 1, 1);
        assertThat(getCellValues(left.getCellsOfRow(4))).containsExactly(0, 0, 2);

        final GridModel<Integer> top = viewModel.getTopNumberGridModel();
        assertThat(top.getNumberOfColumns()).isEqualTo(5);
        assertThat(top.getNumberOfRows()).isEqualTo(3);

        assertThat(getCellValues(top.getCellsOfColumn(0))).containsExactly(1, 1, 1);
        assertThat(getCellValues(top.getCellsOfColumn(1))).containsExactly(0, 0, 5);
        assertThat(getCellValues(top.getCellsOfColumn(2))).containsExactly(0, 0, 0);
        assertThat(getCellValues(top.getCellsOfColumn(3))).containsExactly(0, 0, 1);
        assertThat(getCellValues(top.getCellsOfColumn(4))).containsExactly(0, 0, 1);


    }

    @Test
    public void testWidthAndHeightOfCenterAndOverviewPane(){
        gameInstance = new GameInstance(puzzle3);
        viewModel = new PuzzleViewModel(puzzle3, gameInstance);

        viewModel.rootHeightProperty().set(100);
        viewModel.rootWidthProperty().set(100);

        // (full height / (number of center cells + number of overview cells)) * number of center cells
        // (100 / (3 + 2)) * 3
        assertThat(viewModel.centerHeightProperty()).hasValue((100 / (3+2)) *3);
        assertThat(viewModel.centerWidthProperty()).hasValue((100 / (3+2)) *3);


        // (full height / (number of center cells + number of overview cells)) * number of overview cells
        assertThat(viewModel.overviewHeight()).hasValue((100 / (3+2)) * 2);
        assertThat(viewModel.overviewWidth()).hasValue((100 / (3+2)) * 2);

    }

    private List<Integer> getCellValues(List<Cell<Integer>> cells) {
        return cells.stream().map(Cell::getState).collect(Collectors.toList());
    }
}
