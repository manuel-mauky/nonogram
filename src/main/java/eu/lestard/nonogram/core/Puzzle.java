package eu.lestard.nonogram.core;

import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;

import java.util.ArrayList;
import java.util.List;

public class Puzzle {

    private final int size;


    private static enum PuzzleFill {
        EMPTY,
        FILLED
    }

    private GridModel<PuzzleFill> gridModel = new GridModel<>();

    public Puzzle(int size) {
        this.size = size;

        gridModel.setDefaultState(PuzzleFill.EMPTY);

        gridModel.setNumberOfColumns(size);
        gridModel.setNumberOfRows(size);
    }

    public void addPoint(int columnNr, int rowNr) {
        if (columnNr >= 0 && columnNr < size && rowNr >= 0 && rowNr < size) {
            gridModel.getCell(columnNr, rowNr).changeState(PuzzleFill.FILLED);
        }
    }

    public boolean isPoint(int column, int row) {
        return gridModel.getCell(column,row).getState() == PuzzleFill.FILLED;
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


    private List<Integer> getNumberBlocks(List<Cell<PuzzleFill>> cells) {
        List<Integer> numbers = new ArrayList<>();

        int tmp = 0;

        for (Cell<PuzzleFill> cell : cells) {
            if(cell.getState() == PuzzleFill.FILLED){
                tmp++;
                continue;
            }

            if(cell.getState() == PuzzleFill.EMPTY && tmp > 0){
                numbers.add(tmp);
                tmp = 0;
            }
        }

        if(tmp > 0){
            numbers.add(tmp);
        }

        return numbers;
    }

}