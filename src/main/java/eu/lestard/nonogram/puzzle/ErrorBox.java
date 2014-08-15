package eu.lestard.nonogram.puzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ErrorBox extends Pane {

    private static final int SIZE = 25;

    public ErrorBox(boolean isError){

        if(isError){
            Cross cross = new Cross(Color.RED);

            cross.setMaxHeight(SIZE);
            cross.setMinHeight(SIZE);

            cross.setMaxWidth(SIZE);
            cross.setMinWidth(SIZE);

            this.getChildren().add(cross);
        }

        Rectangle border = new Rectangle(SIZE, SIZE);
        border.setStroke(Color.BLACK);
        border.setFill(Color.TRANSPARENT);

        this.getChildren().add(border);
    }

}
