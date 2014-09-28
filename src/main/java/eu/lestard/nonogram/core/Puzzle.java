package eu.lestard.nonogram.core;

import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Puzzle {

    private int size;


    private GridModel<Boolean> gridModel = new GridModel<>();

    public void setSize(int size){
        this.size = size;
        gridModel.setDefaultState(false);

        gridModel.setNumberOfColumns(size);
        gridModel.setNumberOfRows(size);
    }

    public void addPoint(int columnNr, int rowNr) {
        if (columnNr >= 0 && columnNr < size && rowNr >= 0 && rowNr < size) {
            gridModel.getCell(columnNr, rowNr).changeState(true);
        }
    }

    public boolean isPoint(int column, int row) {
        return gridModel.getCell(column,row).getState();
    }

    public int getSize() {
        return size;
    }

    public List<Integer> getColumnNumbers(int i) {
        return getNumberBlocks(gridModel.getCellsOfColumn(i));
    }

    public List<Integer> getRowNumbers(int i) {
        return getNumberBlocks(gridModel.getCellsOfRow(i));
    }


    private List<Integer> getNumberBlocks(List<Cell<Boolean>> cells) {
        List<Integer> numbers = new ArrayList<>();

        int tmp = 0;

        for (Cell<Boolean> cell : cells) {
            if(cell.getState()){
                tmp++;
            } else if(tmp > 0){
                numbers.add(tmp);
                tmp = 0;
            }
        }

        if(tmp > 0){
            numbers.add(tmp);
        }

        return numbers;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Puzzle (size=").append(size).append(")\n");

        for(int row=0 ; row<size ; row++){
            for(int column=0 ; column<size ; column++){
                result.append("[");
                if(isPoint(column, row)){
                    result.append("x");
                }else{
                    result.append(" ");
                }
                result.append("]");
            }
            result.append("\n");
        }

        return result.toString();
    }
}