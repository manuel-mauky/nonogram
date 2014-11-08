package eu.lestard.nonogram.main;

import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.nonogram.core.GameInstance;
import eu.lestard.nonogram.core.GameManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewModel implements ViewModel {

    private ReadOnlyIntegerWrapper maxErrors = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper currentErrors = new ReadOnlyIntegerWrapper(0);

    private IntegerProperty size = new SimpleIntegerProperty(10);

    private List<Integer> sizeOptions = new ArrayList<>();

    public MainViewModel(GameManager gameManager, GameInstance gameInstance){
        maxErrors.set(gameInstance.maxErrors().get());
        currentErrors.bind(gameInstance.errors());

        sizeOptions.add(10);
        size.set(10);
        sizeOptions.add(15);
        sizeOptions.add(20);
        sizeOptions.add(25);

        size.addListener((obs,oldV, newV)-> gameManager.setSize(newV.intValue()));
    }

    public ReadOnlyIntegerProperty maxErrorsProperty(){
        return maxErrors.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty currentErrorsProperty(){
        return currentErrors.getReadOnlyProperty();
    }

    public List<Integer> getSizeOptions(){
        return Collections.unmodifiableList(sizeOptions);
    }

    public IntegerProperty sizeProperty(){
        return size;
    }

}
