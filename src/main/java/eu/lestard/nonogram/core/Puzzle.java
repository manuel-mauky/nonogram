package eu.lestard.nonogram.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Puzzle {

    private final int size;


    private final List<NumberBlock> vertical = new ArrayList<>();

    private final List<NumberBlock> horizontal = new ArrayList<>();

    public Puzzle(int size){
        this.size = size;
    }

    boolean validateNumbers(Integer...numbers){
        int sum = 0;

        for(int i=0 ; i<numbers.length ; i++){
            sum += numbers[i];
            if(sum > size){
                return false;
            }

            sum++;
        }

        return true;
    }

    public void addVerticalBlock(Integer...numbers){
        if(vertical.size() >= size){
            throw new IllegalArgumentException("There are already all vertical number blocks defined");
        }

        if(validateNumbers(numbers)){
            vertical.add(new NumberBlock(numbers));
        }else{
            throw new IllegalArgumentException("Wrong combination of numbers");
        }
    }

    public void addHorizontalBlock(Integer...numbers){
        if(horizontal.size() >= size){
            throw new IllegalArgumentException("There are already all hhorizontal number blocks defined");
        }

        if(validateNumbers(numbers)){
            horizontal.add(new NumberBlock(numbers));
        }else{
            throw new IllegalArgumentException("Wrong combination of numbers");
        }
    }


    public List<NumberBlock> getVertical(){
        return Collections.unmodifiableList(vertical);
    }

    public List<NumberBlock> getHorizontal(){
        return Collections.unmodifiableList(horizontal);
    }

    public int getSize(){
        return size;
    }
}
