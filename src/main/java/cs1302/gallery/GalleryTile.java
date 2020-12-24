package cs1302.gallery;

import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.io.FileInputStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.lang.Thread;
import java.lang.Runnable;

/**
 * This class contains the images for the {@code GalleryApp}.
 */
public class GalleryTile extends TilePane {

    /**
     * Holds the holders of images.
     */
    private ImageView[] pics;

    /**
     * The present running instance of the {@code GalleryApp}.
     */
    private GalleryApp app;

    /**
     * Controls the shuffling of images every two seconds.
     */
    private Timeline timeline;

    /**
     * This constructs a {@code GalleryTile} object.
     * @param g the active instance of {@code GalleryApp}
     */
    public GalleryTile(GalleryApp g) {
        this.pics = new ImageView[20];
        this.app = g;
        app.setQuery("jazz");
        for (int i = 0; i < 20; i++) {
            pics[i] = new ImageView(new Image("file:/resources/scr_voot.com-3d7e4e3aab_a74926.png",
            100, 100, false, false));
            this.getChildren().add(pics[i]);
        } // for
    } // GalleryTile

    /**
     * Shuffles images relavant current query into view.
     */
    public void picShuffle() {
        Runnable r = () -> {
            EventHandler<ActionEvent> handler = event -> {
                int view = (int) Math.round(19 * Math.random());
                int adjust = app.getContent().size() - 20;
                int img = (int) Math.round(adjust * Math.random() + 19);
                Platform.runLater(() ->
                    pics[view].setImage(new Image(app.getContent().get(img))));
            } ;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), handler);
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
        } ;
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    } // picShuffle

    /**
     * sets the initial images displayed from a query.
     */
    public void picReset() {
        Runnable r = () -> {
            LinkedList<Image> dnld = new LinkedList<>();
            for (int i = 0; i < 20; i++) {
                String surl = app.getContent().get(i);
                dnld.add(new Image(surl));
                app.getLoad().setProgress(app.getLoad().getProgress() + .05);
            } // for
            for (int i = 0; i < 20; i++) {
                this.picSet(i, dnld.get(i));
            } // for
            app.getLoad().setProgress(1);
        } ;
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    } // picReset

    /**
     * Initializes and sets new images into individual {@code ImageView}
     * objects.
     * @param index the index of {@code content}
     * @param musicArt the image to be added to view
     */
    private void picSet(int index, Image musicArt) {
        Platform.runLater(() -> pics[index].setImage(musicArt));
    } // picSet

    /**
     * returns the {@code pics} array.
     * @return pics the array of images
     */
    public ImageView[] getPics() {
        return pics;
    } // getPics

    /**
     * sets the value of {@code pics}.
     * @param photo the new value
     */
    public void setPics(ImageView[] photo) {
        this.pics = photo;
    } // setPics

    /**
     * returns the {@code timeline} for play/pause control.
     * @return timeline controls image shuffling
     */
    public Timeline getTime() {
        return this.timeline;
    }

} // GalleryTile
