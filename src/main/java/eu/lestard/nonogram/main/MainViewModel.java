package eu.lestard.nonogram.main;

import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.nonogram.core.GameInstance;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public class MainViewModel implements ViewModel {

    private ReadOnlyIntegerWrapper maxErrors = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper currentErrors = new ReadOnlyIntegerWrapper(0);

    public MainViewModel(GameInstance gameInstance){
        maxErrors.set(gameInstance.maxErrors().get());
        currentErrors.bind(gameInstance.errors());

    }

    public ReadOnlyIntegerProperty maxErrorsProperty(){
        return maxErrors.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty currentErrorsProperty(){
        return currentErrors.getReadOnlyProperty();
    }


}
