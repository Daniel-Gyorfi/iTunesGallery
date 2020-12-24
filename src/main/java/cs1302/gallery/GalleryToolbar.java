package cs1302.gallery;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.File;

/**
 * This class handles the functionality of the gallery toolbar.
 */
public class GalleryToolbar extends HBox {

    /**
     * This button pauses the replacement of images on the {@code TilePane}.
     */
    private Button pause;

    /**
     * Queries for new images are taken by this {@code TextField}.
     */
    private TextField search;

    /**
     * This button updates the images with the result of the query in
     * {@code search}.
     */
    private Button update;

    /**
     * The running instance of the app.
     */
    private GalleryApp app;

    /**
     * Constructs a new {@code GalleryToolbar} with a set spacing.
     * @param g an instance of {@code GalleryApp}
     */
    public GalleryToolbar(GalleryApp g) {
        this.pause = new Button("Pause");
        EventHandler<ActionEvent> shuffle = f -> {
            g.setPause(!g.getPause());
            if (g.getPause()) {
                pause.setText("play");
                g.getTile().getTime().pause();
            } else {
                pause.setText("pause");
                g.getTile().getTime().play();
            }
        } ;
        pause.setOnAction(shuffle);
        this.search = new TextField("jazz");
        this.update = new Button("Update Images");
        this.app = g;
        EventHandler<ActionEvent> click = f -> {
            g.setQuery(search.getText());
            g.imageGather(); } ;
        update.setOnAction(click);
        this.getChildren().addAll(pause, search, update);
    } // constructor

    /**
     * returns the searchbar.
     * @return search the textfield
     */
    public TextField getSearch() {
        return this.search;
    } // get Search

} // GalleryToolbar
