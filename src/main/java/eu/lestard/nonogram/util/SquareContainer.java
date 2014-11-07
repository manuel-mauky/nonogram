package eu.lestard.nonogram.util;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class SquareContainer extends Region {

    private StackPane root = new StackPane();

    private ObjectProperty<Region> content = new SimpleObjectProperty<>();

    private final NumberBinding size = Bindings.min(this.heightProperty(), this.widthProperty());

    public SquareContainer(){
        this.getChildren().add(root);

        root.prefWidthProperty().bind(this.widthProperty());
        root.prefHeightProperty().bind(this.heightProperty());

        content.addListener((obs, oldContent, newContent)-> {
            if(oldContent != null){
                oldContent.minWidthProperty().unbind();
                oldContent.minHeightProperty().unbind();

                oldContent.maxWidthProperty().unbind();
                oldContent.maxHeightProperty().unbind();

                root.getChildren().remove(oldContent);
            }

            if(newContent != null){
                newContent.minWidthProperty().bind(size);
                newContent.minHeightProperty().bind(size);

                newContent.maxWidthProperty().bind(size);
                newContent.maxHeightProperty().bind(size);

                root.getChildren().add(newContent);
            }
        });
    }

    public void setContent(Region content){
        this.content.set(content);
    }

    public Region getContent(){
        return content.get();
    }
}
