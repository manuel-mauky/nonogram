package eu.lestard.nonogram.puzzle;

import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.nonogram.core.Puzzle;
import eu.lestard.nonogram.core.State;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.scene.input.MouseButton;

import java.util.List;

public class PuzzleViewModel implements ViewModel {

    private static final int MAX_ERRORS = 5;

    private GridModel<State> mainGridModel;

    private GridModel<Integer> topNumberGridModel;
    private GridModel<Integer> leftNumberGridModel;

    private ReadOnlyIntegerWrapper maxErrors = new ReadOnlyIntegerWrapper(MAX_ERRORS);
    private ReadOnlyIntegerWrapper currentErrors = new ReadOnlyIntegerWrapper(0);

    private ReadOnlyBooleanWrapper gameOver = new ReadOnlyBooleanWrapper();

    private ReadOnlyIntegerWrapper size = new ReadOnlyIntegerWrapper();

    public PuzzleViewModel(){
        topNumberGridModel = new GridModel<>();
        topNumberGridModel.setDefaultState(0);

        leftNumberGridModel = new GridModel<>();
        leftNumberGridModel.setDefaultState(0);

        mainGridModel = new GridModel<>();
        mainGridModel.setDefaultState(State.EMPTY);

    }

    public void init(Puzzle puzzle){

        size.set(puzzle.getSize());

        mainGridModel.setNumberOfColumns(puzzle.getSize());
        mainGridModel.setNumberOfRows(puzzle.getSize());

        mainGridModel.getCells().forEach(cell->{

            cell.setOnClick(event -> {
                if(gameOver.get()){
                    return;
                }

                if (event.getButton() == MouseButton.PRIMARY) {
                    onPrimaryDown(puzzle, cell);
                }

                if(event.getButton() == MouseButton.SECONDARY){
                    onSecondaryDown(cell);
                }
            });

            cell.setOnMouseOver(event -> {
                if(gameOver.get()){
                    return;
                }

                if (event.isPrimaryButtonDown()) {
                    onPrimaryDown(puzzle, cell);
                }

                if(event.isSecondaryButtonDown()){
                    onSecondaryDown(cell);
                }
            });
        });

        topNumberGridModel.setNumberOfColumns(puzzle.getSize());
        topNumberGridModel.setNumberOfRows(puzzle.getSize()/2);

        leftNumberGridModel.setNumberOfColumns(puzzle.getSize()/2);
        leftNumberGridModel.setNumberOfRows(puzzle.getSize());


        for(int i=0 ; i<puzzle.getSize() ; i++){

            final List<Integer> rowNumbers = puzzle.getRowNumbers(i);

            for (int vertical = 0; vertical < rowNumbers.size(); vertical++) {
                int offset = puzzle.getSize()/2 - rowNumbers.size();
                final Cell<Integer> cell = leftNumberGridModel.getCell(offset + vertical, i);
                cell.changeState(rowNumbers.get(vertical));
            }


            final List<Integer> columnNumbers = puzzle.getColumnNumbers(i);

            for (int horizontal = 0; horizontal < columnNumbers.size(); horizontal++) {
                int offset = puzzle.getSize()/2 - columnNumbers.size();
                final Cell<Integer> cell = topNumberGridModel.getCell(i, offset + horizontal);
                cell.changeState(columnNumbers.get(horizontal));
            }
        }


        gameOver.bind(currentErrors.greaterThanOrEqualTo(maxErrors));
    }

    private void onPrimaryDown(Puzzle puzzle, Cell<State> cell) {
        if (cell.getState() == State.FILLED) {
            return;
        }

        if (cell.getState() == State.MARKED) {
            cell.changeState(State.EMPTY);
            return;
        }

        if (puzzle.isPoint(cell.getColumn(), cell.getRow())) {
            cell.changeState(State.FILLED);
        } else {
            currentErrors.set(currentErrors.get() + 1);
            cell.changeState(State.ERROR);
        }
    }

    private void onSecondaryDown(Cell<State> cell) {
        if (cell.getState() == State.EMPTY) {
            cell.changeState(State.MARKED);
        }else if (cell.getState() == State.MARKED){
            cell.changeState(State.EMPTY);
        }
    }


    public GridModel<State> getCenterGridModel(){
        return mainGridModel;
    }

    public GridModel<State> getOverviewGridModel(){
        return mainGridModel;
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
