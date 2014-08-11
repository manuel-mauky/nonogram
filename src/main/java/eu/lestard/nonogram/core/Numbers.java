package eu.lestard.nonogram.core;

import java.util.Arrays;

public enum Numbers {

    EMPTY(0),

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);


    private int i;

    Numbers(int i){
        this.i = i;
    }

    public int getNumber(){
        return i;
    }

    public static Numbers getByInteger(int i){
        return Arrays.stream(Numbers.values()).filter(e -> e.i == i).findFirst().get();
    }

}
