package cs1302.gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import javafx.stage.Modality;
import java.net.MalformedURLException;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.util.LinkedList;

/**
 * Represents an iTunes {@code GalleryApp}.
 */
public class GalleryApp extends Application {

    private GalleryToolbar toolbar;
    private GalleryTile picView;
    private GalleryLoad load;
    private LinkedList<String> content;
    private String query;
    private boolean pause;

    /** {@inheritDoc}. */
    @Override
    public void start(Stage stage) {
        stage = new Stage();
        content = new LinkedList<String>();
        VBox pane = new VBox(4);
        VBox extra = new VBox(4);
        MenuBar menu = new MenuBar();
        Menu file = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> Platform.exit());
        MenuItem about = new MenuItem("About");
        Stage info = new Stage();
        info.initOwner(stage);
        info.initModality(Modality.APPLICATION_MODAL);
        Scene disp = new Scene(extra);
        info.setScene(disp);
        info.setTitle("About Daniel Gyorfi");
        info.sizeToScene();
        Button off = new Button("Exit");
        off.setOnAction(event -> info.close());
        ImageView face = new ImageView(new Image("file:resources/me.jpg"));
        Text bio = new Text("Daniel Gyorfi");
        Text email = new Text("dg32081@uga.edu");
        Text version = new Text("version 1.2");
        extra.getChildren().addAll(off, face, bio, email, version);
        about.setOnAction(event -> {
            info.showAndWait();
        } );
        Menu help = new Menu("Help");
        help.getItems().add(about);
        file.getItems().addAll(exit);
        menu.getMenus().addAll(file, help);
        pause = false;
        toolbar = new GalleryToolbar(this);
        load = new GalleryLoad(this);
        picView = new GalleryTile(this);
        pane.getChildren().addAll(menu, toolbar, picView, load);
        this.imageGather();
        picView.picShuffle();
        Scene scene = new Scene(pane);
        stage.setMaxWidth(500);
        stage.setMaxHeight(490);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(490);
        stage.setResizable(false);
        stage.show();
    } // start

    /**
     * This method acquires a JSON object from iTunes and accesses the images
     * from the data.
     */
    public void imageGather() {
        load.setProgress(0.0);
        LinkedList<String> dist = new LinkedList<>();
        String search = query.trim().replace(" ", "+");
        String iquery = "https://itunes.apple.com/search?term=" + search
            + "&limit=200&media=music&country=us";
        try {
            URL iReq = new URL(iquery);
            InputStreamReader access = new InputStreamReader(iReq.openStream());
            JsonArray iRec = JsonParser.parseReader(access).getAsJsonObject().
                getAsJsonArray("results");
            int iLength = iRec.size();
            this.content.clear();
            JsonElement artURL = null;
            for (int i = 0; i < iLength; i++) {
                try {
                    artURL = iRec.get(i).getAsJsonObject().get("artworkUrl100");
                } catch (Exception e) {
                    continue;
                } // try-catch
                String art = artURL.getAsString();
                dist.add(art);
                if (dist.indexOf(art) == dist.lastIndexOf(art) && dist.indexOf(art) != -1) {
                    content.add(art);
                } // if
            } // for
            if (content.size() < 21) {
                this.getTool().getSearch().setText("Not Enough Found");
            } else {
                picView.picReset();
            } //if else
        } catch (MalformedURLException f) {
            System.out.println("URL fucked up");
        } catch (IOException f) {
            this.getTool().getSearch().setText("No files received");
        } catch (IllegalStateException f) {
            System.out.println("IllegalStateException");
        } catch (IllegalArgumentException f) {
            System.out.println("Invalid URL");
        }
    } // imageGather

    /**
     * Returns the {@code toolbar}.
     * @return tool the {@code toolbar} reference.
     */
    public GalleryToolbar getTool() {
        return toolbar;
    } // getTool

    /**
     * Returns {@code load}.
     * @return load the {@code HBox} containing a {@code ProgressBar}
     */
    public GalleryLoad getLoad() {
        return load;
    } // getLoad

    /**
     * Returns the content.
     * @return content current list contents
     */
    public LinkedList<String> getContent() {
        return content;
    }

    /**
     * returns the saved query.
     * @return query the search term
     */
    public String getQuery() {
        return query;
    }

    /**
     * returns {@code pause}.
     * @return pause boolean pause/play status
     */
    public boolean getPause() {
        return pause;
    } // getPause

    /**
     * returns {@code picView}.
     * @return picView the custom {@code TilePane}
     */
    public GalleryTile getTile() {
        return this.picView;
    } // getTile

    /**
     * sets variable {@code content}.
     * @param cont the new value for {@code content}
     */
    public void setContent(LinkedList<String> cont) {
        content = cont;
    }

    /**
     * sets the {@code query} variable.
     * @param look the new {@code query}
     */
    public void setQuery(String look) {
        query = look;
    } // setQuery

    /**
     * sets {@code pause} based on {@code play}.
     * @param play the new {@code pause} status
     */
    public void setPause(boolean play) {
        pause = play;
    } // setPause

} // GalleryApp
