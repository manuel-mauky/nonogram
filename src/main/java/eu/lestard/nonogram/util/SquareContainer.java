package eu.lestard.nonogram.util;

import javafx.beans.NamedArg;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class SquareContainer extends Region {

    private StackPane root = new StackPane();

    private Pane content;

    public SquareContainer(@NamedArg("content") Pane content){
        this.content = content;
        this.getChildren().add(root);

        final NumberBinding size = Bindings.min(this.heightProperty(), this.widthProperty());

        content.minWidthProperty().bind(size);
        content.minHeightProperty().bind(size);

        content.maxWidthProperty().bind(size);
        content.maxHeightProperty().bind(size);


        root.getChildren().add(content);

        root.prefWidthProperty().bind(this.widthProperty());
        root.prefHeightProperty().bind(this.heightProperty());
    }

    public SquareContainer(){
        this(new Pane());
    }

    public Pane getContent(){
        return content;
    }

    @Override
    public ObservableList<Node> getChildrenUnmodifiable() {
        return content.getChildrenUnmodifiable();
    }
}
