package eu.lestard.nonogram.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a single row/column of numbers in a {@link Puzzle}.
 *
 * The {@link Puzzle} contains many horizontal and vertical {@link NumberBlock}'s
 */
public class NumberBlock {

    private List<Integer> numbers = new ArrayList<>();

    public NumberBlock(Integer ... n){
        this.numbers.addAll(Arrays.asList(n));
    }

    public List<Integer> getNumbers(){
        return Collections.unmodifiableList(numbers);
    }
}
