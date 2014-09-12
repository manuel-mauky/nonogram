package eu.lestard.nonogram.puzzle;

import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.nonogram.core.GameInstance;
import eu.lestard.nonogram.core.Puzzle;
import eu.lestard.nonogram.core.State;
import javafx.beans.property.*;
import javafx.scene.input.MouseButton;

import java.util.List;

public class PuzzleViewModel implements ViewModel {

    private ReadOnlyObjectWrapper<GridModel<State>> mainGridModel = new ReadOnlyObjectWrapper<>();

    private GridModel<Integer> topNumberGridModel;
    private GridModel<Integer> leftNumberGridModel;

    private ReadOnlyIntegerWrapper size = new ReadOnlyIntegerWrapper();
    private GameInstance gameInstance;

    private ReadOnlyBooleanWrapper gameOver = new ReadOnlyBooleanWrapper();

    private ReadOnlyIntegerWrapper maxErrors = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper currentErrors = new ReadOnlyIntegerWrapper(0);



    public PuzzleViewModel(){
        topNumberGridModel = new GridModel<>();
        topNumberGridModel.setDefaultState(0);

        leftNumberGridModel = new GridModel<>();
        leftNumberGridModel.setDefaultState(0);
    }

    public void init(Puzzle puzzle, GameInstance gameInstance){
        this.gameInstance = gameInstance;
        maxErrors.set(gameInstance.maxErrors().get());
        currentErrors.bind(gameInstance.errors());

        gameOver.bind(gameInstance.gameOver());

        mainGridModel.set(gameInstance.getGridModel());

        size.set(puzzle.getSize());

        gameInstance.getGridModel().getCells().forEach(cell -> {
            cell.setOnClick(event -> {
                if (gameInstance.gameOver().get()) {
                    return;
                }

                if (event.getButton() == MouseButton.PRIMARY) {
                    onPrimaryDown(puzzle, cell);
                }

                if (event.getButton() == MouseButton.SECONDARY) {
                    onSecondaryDown(cell);
                }
            });

            cell.setOnMouseOver(event -> {
                if (gameInstance.gameOver().get()) {
                    return;
                }

                if (event.isPrimaryButtonDown()) {
                    onPrimaryDown(puzzle, cell);
                }

                if (event.isSecondaryButtonDown()) {
                    onSecondaryDown(cell);
                }
            });
        });


        topNumberGridModel.setNumberOfColumns(puzzle.getSize());
        topNumberGridModel.setNumberOfRows((int)Math.ceil(puzzle.getSize() / 2.0));

        leftNumberGridModel.setNumberOfColumns((int)Math.ceil(puzzle.getSize() / 2.0));
        leftNumberGridModel.setNumberOfRows(puzzle.getSize());

        for(int i=0 ; i<puzzle.getSize() ; i++){

            final List<Integer> rowNumbers = puzzle.getRowNumbers(i);

            for (int vertical = 0; vertical < rowNumbers.size(); vertical++) {
                int offset = leftNumberGridModel.getNumberOfColumns() - rowNumbers.size();
                final Cell<Integer> cell = leftNumberGridModel.getCell(offset + vertical, i);
                cell.changeState(rowNumbers.get(vertical));
            }


            final List<Integer> columnNumbers = puzzle.getColumnNumbers(i);

            for (int horizontal = 0; horizontal < columnNumbers.size(); horizontal++) {
                int offset = topNumberGridModel.getNumberOfRows() - columnNumbers.size();
                final Cell<Integer> cell = topNumberGridModel.getCell(i, offset + horizontal);
                cell.changeState(columnNumbers.get(horizontal));
            }
        }
    }

    private void onPrimaryDown(Puzzle puzzle, Cell<State> cell) {
        gameInstance.reveal(cell.getColumn(), cell.getRow());
    }

    private void onSecondaryDown(Cell<State> cell) {
        gameInstance.mark(cell.getColumn(), cell.getRow());
    }

    public ReadOnlyObjectProperty<GridModel<State>> centerGridModelProperty(){
        return mainGridModel.getReadOnlyProperty();
    }

    public ReadOnlyObjectProperty<GridModel<State>> overviewGridModelProperty(){
        return mainGridModel.getReadOnlyProperty();
    }

    public GridModel<Integer> getLeftNumberGridModel(){
        return leftNumberGridModel;
    }

    public GridModel<Integer> getTopNumberGridModel(){
        return topNumberGridModel;
    }


    public ReadOnlyIntegerProperty maxErrorsProperty(){
        return maxErrors.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty currentErrorsProperty(){
        return currentErrors.getReadOnlyProperty();
    }

    public ReadOnlyBooleanProperty gameOverProperty(){
        return gameOver.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty sizeProperty(){
        return size.getReadOnlyProperty();
    }
}
