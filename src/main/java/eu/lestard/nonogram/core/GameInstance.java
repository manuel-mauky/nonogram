package eu.lestard.nonogram.core;

import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the current "state" of the game. It can answer questions like
 * 'Which fields are already revealed?' , "Which are marked?" or "How many errors does the user made?".
 */
public class GameInstance {

    private static final int MAX_ERRORS = 5;

    private ReadOnlyIntegerWrapper maxErrors = new ReadOnlyIntegerWrapper(MAX_ERRORS);
    private ReadOnlyIntegerWrapper currentErrors = new ReadOnlyIntegerWrapper(0);


    private Puzzle puzzle;

    private Map<Integer, List<Integer>> columnNumberBlocks = new HashMap<>();

    private Map<Integer, List<Integer>> rowNumberBlocks = new HashMap<>();

    private GridModel<State> gridModel = new GridModel<>();

    private ReadOnlyIntegerWrapper errors = new ReadOnlyIntegerWrapper(0);

    private ReadOnlyBooleanWrapper gameOver = new ReadOnlyBooleanWrapper();


    /**
     * The list of column/row indexes that are already finished correctly.
     */
    private ObservableList<Integer> finishedColumns = FXCollections.observableArrayList();
    private ObservableList<Integer> finishedRows = FXCollections.observableArrayList();



    public GameInstance(Puzzle puzzle){
        this.puzzle = puzzle;

        gridModel.setDefaultState(State.EMPTY);

        gridModel.setNumberOfColumns(puzzle.getSize());
        gridModel.setNumberOfRows(puzzle.getSize());
        gameOver.bind(errors.greaterThanOrEqualTo(maxErrors));
    }

    /**
     * Mark the given cell.
     */
    public void mark(int column, int row){
        final Cell<State> cell = gridModel.getCell(column, row);
        if(cell.getState() == State.EMPTY){
            cell.changeState(State.MARKED);
        }else if(cell.getState() == State.MARKED){
            cell.changeState(State.EMPTY);
        }
    }

    /**
     * Reveale the cell with the given coordinates.
     */
    public void reveale(int column, int row){
        final Cell<State> cell = gridModel.getCell(column, row);

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
            errors.set(errors.get() + 1);
            cell.changeState(State.ERROR);
        }
    }

    public GridModel<State> getGridModel(){
        return gridModel;
    }

    public ObservableList<Integer> finishedColumnsList(){
        return FXCollections.unmodifiableObservableList(finishedColumns);
    }

    public ObservableList<Integer> finishedRowsList(){
        return FXCollections.unmodifiableObservableList(finishedRows);
    }


    public ReadOnlyIntegerProperty errors(){
        return errors;
    }

    public ReadOnlyIntegerProperty maxErrors(){
        return maxErrors;
    }

    public ReadOnlyBooleanProperty gameOver(){
        return gameOver;
    }
}
