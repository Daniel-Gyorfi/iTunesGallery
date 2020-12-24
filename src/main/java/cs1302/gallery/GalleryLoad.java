package cs1302.gallery;

import javafx.scene.layout.HBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.application.Platform;

/**
 * This contains the {@code ProgressBar}, which is needed so it can be
 * accessed by other classes at run-time.
 */
public class GalleryLoad extends HBox {

    /**
     * Contains a message about the source of images.
     */
    private Text blurb;

    /**
     * Displays the progress of intensive loading.
     */
    private ProgressBar blue;

    /**
     * The current instance of {@code GalleryApp}.
     */
    private GalleryApp app;

    /**
     * This constructs a {@code GalleryLoad} object.
     * @param g the active instance of {@code GalleryApp}
     */
    public GalleryLoad(GalleryApp g) {
        this.blurb = new Text("Images provided courtesy of iTunes");
        this.blue = new ProgressBar(0.0);
        this.app = g;
        this.getChildren().addAll(blue, blurb);
    } // constructor

    /**
     * returns the current progress of the bar.
     * @return blue the progress;
     */
    public double getProgress() {
        return blue.getProgress();
    } // getProgress

    /**
     * sets the progress of the bar.
     * @param state the new bar progress
     */
    public void setProgress(double state) {
        blue.setProgress(state);
    } // setProgress

}
