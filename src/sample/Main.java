package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.controller.Controller;
import sample.conf.Configuration;

import java.io.File;
import java.nio.file.Paths;

public class Main extends Application {
    private static Stage stage;

    private static Parent app;

    private static Configuration conf;

    private Controller appController;

    public static void main(String[] args) {
        //loadQuestions();
        //createLessons();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.conf = new Configuration();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/Sample.fxml"));
        app = fxmlLoader.load();
        appController = fxmlLoader.getController();
        appController.initialize();

        stage.setTitle("Algareedy");
        stage.initStyle(StageStyle.TRANSPARENT);
        {//blur effect
            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: transparent;");
            root.setTop(null);
            root.setCenter(app);
            stage.setScene(new Scene(root, Color.TRANSPARENT));
        }
        {//not blurred
            //stage.setScene(new Scene(app));
        }
        stage.setWidth(500);
        stage.setHeight(500);
        //stage.setOpacity(1);
        //stage.setAlwaysOnTop(true);
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            appController.notifyFocusState(newValue);
        });
        stage.show();
    }


    public static Stage getStage() {
        return stage;
    }
    public static Parent getParent() {
        return app;
    }
    public static Configuration getConf() {
        return conf;
    }

    public static void loadQuestions() {
        try {
            File configFile = Paths.get(System.getProperty("user.dir"), "nomefile.txt").toFile();
            if (configFile.exists()) {
                //String configContent = FileUtil.readFile(configFile);
                //fileClassName = JSONUtil.JSONToObject(configContent, fileClassName.class);
            } else {
                //hardcoded questions?
            }
        } catch (Exception e) {

        }
    }
}
