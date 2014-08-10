package eu.lestard.nonogram.puzzle;

import de.saxsys.jfx.mvvm.api.ViewModel;
import eu.lestard.grid.GridModel;
import eu.lestard.nonogram.core.Numbers;
import eu.lestard.nonogram.core.State;

public class PuzzleViewModel implements ViewModel {

    private static final int COLUMNS = 20;
    private static final int ROWS = 20;


    private GridModel<State> mainGridModel;

    private GridModel<Numbers> topNumberGridModel;
    private GridModel<Numbers> leftNumberGridModel;


    public PuzzleViewModel(){
        mainGridModel = new GridModel<>();
        mainGridModel.setNumberOfColumns(COLUMNS);
        mainGridModel.setNumberOfRows(ROWS);
        mainGridModel.setDefaultState(State.EMPTY);


        topNumberGridModel = new GridModel<>();
        topNumberGridModel.setNumberOfColumns(COLUMNS);
        topNumberGridModel.setNumberOfRows(ROWS/2);
        topNumberGridModel.setDefaultState(Numbers.EMPTY);

        leftNumberGridModel = new GridModel<>();
        leftNumberGridModel.setNumberOfColumns(COLUMNS/2);
        leftNumberGridModel.setNumberOfRows(ROWS);
        leftNumberGridModel.setDefaultState(Numbers.EMPTY);
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

}
