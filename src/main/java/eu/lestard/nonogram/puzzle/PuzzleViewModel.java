package eu.lestard.nonogram.puzzle;

import de.saxsys.jfx.mvvm.api.ViewModel;
import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.nonogram.core.Numbers;
import eu.lestard.nonogram.core.Puzzle;
import eu.lestard.nonogram.core.State;

import java.util.List;

public class PuzzleViewModel implements ViewModel {

    private GridModel<State> mainGridModel;

    private GridModel<Numbers> topNumberGridModel;
    private GridModel<Numbers> leftNumberGridModel;


    public PuzzleViewModel(){
        mainGridModel = new GridModel<>();
        mainGridModel.setDefaultState(State.EMPTY);

        topNumberGridModel = new GridModel<>();
        topNumberGridModel.setDefaultState(Numbers.EMPTY);

        leftNumberGridModel = new GridModel<>();
        leftNumberGridModel.setDefaultState(Numbers.EMPTY);
    }

    public void setPuzzle(Puzzle puzzle){
        mainGridModel.setNumberOfColumns(puzzle.getSize());
        mainGridModel.setNumberOfRows(puzzle.getSize());

        topNumberGridModel.setNumberOfColumns(puzzle.getSize());
        topNumberGridModel.setNumberOfRows(puzzle.getSize()/2);

        leftNumberGridModel.setNumberOfColumns(puzzle.getSize()/2);
        leftNumberGridModel.setNumberOfRows(puzzle.getSize());


        for(int i=0 ; i<puzzle.getSize() ; i++){
            final List<Integer> verticalNumbers = puzzle.getVertical().get(i).getNumbers();

            for (int vertical = 0; vertical < verticalNumbers.size(); vertical++) {
                int offset = puzzle.getSize()/2 - verticalNumbers.size();
                final Cell<Numbers> cell = leftNumberGridModel.getCell(offset + vertical, i);
                final Numbers newState = Numbers.getByInteger(verticalNumbers.get(vertical));
                cell.changeState(newState);
            }


            final List<Integer> horizontalNumbers = puzzle.getHorizontal().get(i).getNumbers();

            for (int horizontal = 0; horizontal < horizontalNumbers.size(); horizontal++) {
                int offset = puzzle.getSize()/2 - horizontalNumbers.size();
                final Cell<Numbers> cell = topNumberGridModel.getCell(i, offset + horizontal);
                final Numbers newState = Numbers.getByInteger(horizontalNumbers.get(horizontal));
                cell.changeState(newState);
            }

        }
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
