package eu.lestard.nonogram;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import eu.lestard.easydi.EasyDI;
import eu.lestard.nonogram.main.MainView;
import eu.lestard.nonogram.main.MainViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String...args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        EasyDI easyDI = new EasyDI();
        MvvmFX.setCustomDependencyInjector(easyDI::getInstance);

        final ViewTuple<MainView, MainViewModel> viewTuple = FluentViewLoader.fxmlView(MainView.class).load();

        primaryStage.setScene(new Scene(viewTuple.getView(), 600, 600));

        primaryStage.show();
    }
}
