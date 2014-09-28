package eu.lestard.nonogram.puzzle;

import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.nonogram.core.GameInstance;
import eu.lestard.nonogram.core.Puzzle;
import eu.lestard.nonogram.core.State;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableNumberValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;

import java.util.List;

public class PuzzleViewModel implements ViewModel {

    private GridModel<Integer> topNumberGridModel;
    private GridModel<Integer> leftNumberGridModel;

    private GameInstance gameInstance;
    private Puzzle puzzle;

    private ReadOnlyIntegerWrapper maxErrors = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper currentErrors = new ReadOnlyIntegerWrapper(0);


    private ReadOnlyDoubleWrapper centerWidth = new ReadOnlyDoubleWrapper();
    private ReadOnlyDoubleWrapper centerHeight = new ReadOnlyDoubleWrapper();
    private ReadOnlyDoubleWrapper overviewWidth = new ReadOnlyDoubleWrapper();
    private ReadOnlyDoubleWrapper overviewHeight = new ReadOnlyDoubleWrapper();


    private DoubleProperty rootWidth = new SimpleDoubleProperty();
    private DoubleProperty rootHeight = new SimpleDoubleProperty();


    private ObservableList<Integer> finishedColumns = FXCollections.observableArrayList();
    private ObservableList<Integer> finishedRows = FXCollections.observableArrayList();

    public PuzzleViewModel(Puzzle puzzle, GameInstance gameInstance){
        topNumberGridModel = new GridModel<>();
        topNumberGridModel.setDefaultState(0);

        leftNumberGridModel = new GridModel<>();
        leftNumberGridModel.setDefaultState(0);

        this.gameInstance = gameInstance;
        this.puzzle = puzzle;
        init();
    }

    void init(){
        maxErrors.set(gameInstance.maxErrors().get());
        currentErrors.bind(gameInstance.errors());

        gameInstance.getGridModel().getCells().forEach(cell -> {
            cell.setOnClick(event -> {
                if (gameInstance.gameOver().get()) {
                    return;
                }

                if (event.getButton() == MouseButton.PRIMARY) {
                    this.gameInstance.revealWithSingleClick(cell);
                }

                if (event.getButton() == MouseButton.SECONDARY) {
                    this.gameInstance.markWithSingleClick(cell);
                }
            });

            cell.setOnMouseOver(event -> {
                if (gameInstance.gameOver().get()) {
                    return;
                }

                if (event.isPrimaryButtonDown()) {
                    this.gameInstance.revealWithMouseOver(cell);
                }

                if (event.isSecondaryButtonDown()) {
                    this.gameInstance.markWithMouseOver(cell);
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


        ObservableNumberValue numberOfCenterColumns = gameInstance.getGridModel().numberOfColumns();
        ObservableNumberValue numberOfCenterRows = gameInstance.getGridModel().numberOfRows();

        final IntegerProperty numberOfLeftColumns = leftNumberGridModel.numberOfColumns();
        final NumberBinding numberOfColumns = numberOfLeftColumns.add(numberOfCenterColumns);
        final DoubleBinding widthOfEveryColumn = rootWidth.divide(numberOfColumns);

        final IntegerProperty numberOfTopRows = topNumberGridModel.numberOfRows();
        final NumberBinding numberOfRows = numberOfTopRows.add(numberOfCenterRows);
        final DoubleBinding heightOfEveryRow = rootHeight.divide(numberOfRows);


        centerWidth.bind(widthOfEveryColumn.multiply(numberOfCenterColumns));
        centerHeight.bind(heightOfEveryRow.multiply(numberOfCenterRows));


        overviewWidth.bind(widthOfEveryColumn.multiply(numberOfLeftColumns));
        overviewHeight.bind(heightOfEveryRow.multiply(numberOfTopRows));

        Bindings.bindContent(finishedRows, gameInstance.finishedRowsList());
        Bindings.bindContent(finishedColumns, gameInstance.finishedColumnsList());
    }

    public GridModel<State> getCenterGridModel(){
        return gameInstance.getGridModel();
    }

    public GridModel<State> getOverviewGridModel(){
        return gameInstance.getGridModel();
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
        return gameInstance.gameOver();
    }

    public ReadOnlyBooleanProperty gameFinishedProperty(){
        return gameInstance.win();
    }

    public int getSize(){
        return puzzle.getSize();
    }

    public ReadOnlyDoubleProperty centerWidthProperty(){
        return centerWidth;
    }

    public ReadOnlyDoubleProperty centerHeightProperty(){
        return centerHeight;
    }

    public ReadOnlyDoubleProperty overviewWidth(){
        return overviewWidth;
    }

    public ReadOnlyDoubleProperty overviewHeight(){
        return overviewHeight;
    }

    public DoubleProperty rootWidthProperty(){
        return rootWidth;
    }

    public DoubleProperty rootHeightProperty(){
        return rootHeight;
    }

    public ObservableList<Integer> finishedColumns(){
        return finishedColumns;
    }

    public ObservableList<Integer> finishedRows(){
        return finishedRows;
    }


}
