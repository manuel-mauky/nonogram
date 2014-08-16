package eu.lestard.nonogram.puzzle;

import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.nonogram.core.Numbers;
import eu.lestard.nonogram.core.Puzzle;
import eu.lestard.nonogram.core.State;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class PuzzleViewModel implements ViewModel {

    private static final int MAX_ERRORS = 5;

    private GridModel<State> mainGridModel;

    private GridModel<Numbers> topNumberGridModel;
    private GridModel<Numbers> leftNumberGridModel;

    private ReadOnlyIntegerWrapper maxErrors = new ReadOnlyIntegerWrapper(MAX_ERRORS);
    private ReadOnlyIntegerWrapper currentErrors = new ReadOnlyIntegerWrapper(0);

    private ReadOnlyBooleanWrapper gameOver = new ReadOnlyBooleanWrapper();

    public PuzzleViewModel(){
        topNumberGridModel = new GridModel<>();
        topNumberGridModel.setDefaultState(Numbers.EMPTY);

        leftNumberGridModel = new GridModel<>();
        leftNumberGridModel.setDefaultState(Numbers.EMPTY);

        mainGridModel = new GridModel<>();
        mainGridModel.setDefaultState(State.EMPTY);

    }

    public void init(Puzzle puzzle){

        mainGridModel.setNumberOfColumns(puzzle.getSize());
        mainGridModel.setNumberOfRows(puzzle.getSize());

        mainGridModel.getCells().forEach(cell->{

            final EventHandler<MouseEvent> eventHandler = event -> {
                if(gameOver.get()){
                    return;
                }


                if (event.getButton() == MouseButton.PRIMARY) {

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

                    return;
                }

                if (event.getButton() == MouseButton.SECONDARY) {

                    if (cell.getState() == State.EMPTY) {
                        cell.changeState(State.MARKED);
                        return;
                    }

                    if (cell.getState() == State.MARKED) {
                        cell.changeState(State.EMPTY);
                    }
                }
            };

            cell.setOnClick(eventHandler);
        });

        topNumberGridModel.setNumberOfColumns(puzzle.getSize());
        topNumberGridModel.setNumberOfRows(puzzle.getSize()/2);

        leftNumberGridModel.setNumberOfColumns(puzzle.getSize()/2);
        leftNumberGridModel.setNumberOfRows(puzzle.getSize());


        for(int i=0 ; i<puzzle.getSize() ; i++){

            final List<Integer> rowNumbers = puzzle.getRowNumbers(i);

            for (int vertical = 0; vertical < rowNumbers.size(); vertical++) {
                int offset = puzzle.getSize()/2 - rowNumbers.size();
                final Cell<Numbers> cell = leftNumberGridModel.getCell(offset + vertical, i);
                final Numbers newState = Numbers.getByInteger(rowNumbers.get(vertical));
                cell.changeState(newState);
            }


            final List<Integer> columnNumbers = puzzle.getColumnNumbers(i);

            for (int horizontal = 0; horizontal < columnNumbers.size(); horizontal++) {
                int offset = puzzle.getSize()/2 - columnNumbers.size();
                final Cell<Numbers> cell = topNumberGridModel.getCell(i, offset + horizontal);
                final Numbers newState = Numbers.getByInteger(columnNumbers.get(horizontal));
                cell.changeState(newState);
            }
        }


        gameOver.bind(currentErrors.greaterThanOrEqualTo(maxErrors));

    }


    public GridModel<State> getCenterGridModel(){
        return mainGridModel;
    }

    public GridModel<State> getOverviewGridModel(){
        return mainGridModel;
    }

    public GridModel<Numbers> getLeftNumberGridModel(){
        return leftNumberGridModel;
    }

    public GridModel<Numbers> getTopNumberGridModel(){
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
}
