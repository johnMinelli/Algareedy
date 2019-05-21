package sample.controller;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import sample.conf.Configuration;
import sample.conf.Const;
import sample.Main;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Arrays;

public class Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private BorderPane head;

    @FXML
    private Label headMinimizeButton, headMaximizeButton, headCloseButton;

    @FXML
    private BorderPane body;

    @FXML
    private Pane handPaneMac;

    @FXML
    private VBox sideArea;

    @FXML
    private HBox sideControls;

    @FXML
    private Label sideMinimizeButton, sideMaximizeButton, sideCloseButton;

    @FXML
    private VBox sideNav;

    @FXML
    private Region navPluginsMac, navLessonMac, navQuizMac, navAboutMac, navSettingsMac;

    @FXML
    private Region navPluginsWin, navLessonWin, navQuizWin, navAboutWin, navSettingsWin;

    @FXML
    private Label appUpdateAlarmLabelMac;

    @FXML
    private Label appUpdateAlarmLabelWin;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private BorderPane contentPane;

    @FXML
    private VBox componentBox;

    Configuration conf;

    public void initialize() {
        conf = new Configuration();
        applyTheme();
        {
            makeDraggable(Main.getStage(), head);
            makeNormalizable(Main.getStage(), head);

            makeMinimizable(Main.getStage(), headMinimizeButton);
            makeMaximizable(Main.getStage(), headMaximizeButton);
            makeClosable(Main.getStage(), headCloseButton);
        }
        {
            makeDraggable(Main.getStage(), sideNav);
            makeNormalizable(Main.getStage(), sideNav);

            makeMinimizable(Main.getStage(), sideMinimizeButton);
            makeMaximizable(Main.getStage(), sideMaximizeButton);
            makeClosable(Main.getStage(), sideCloseButton);
        }
        {
            // for Mac theme
            makeDraggable(Main.getStage(), handPaneMac);
            makeNormalizable(Main.getStage(), handPaneMac);
        }
        //wrapper for all events
        Main.getStage().addEventHandler(Const.EVENT_ALL, event -> {onEvent(event.getEventType());});
    }

    private void makeDraggable(final Stage stage, final Node byNode) {
        final Point2D.Double delta = new Point2D.Double();
        byNode.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                delta.setLocation(stage.getX() - mouseEvent.getScreenX(), stage.getY() - mouseEvent.getScreenY());
                byNode.setOpacity(0.98);
            }
        });

        byNode.setOnMouseDragged(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (stage.isMaximized()) {
                    double x = (mouseEvent.getScreenX() - stage.getX());
                    double y = (mouseEvent.getScreenY() - stage.getY());

                    changeMaximizeProperty(stage);

                    delta.setLocation(-1 * (stage.getWidth() / 2),-1 * (mouseEvent.getSceneY()));
                } else {
                    stage.setX(mouseEvent.getScreenX() + delta.getX());
                    stage.setY(mouseEvent.getScreenY() + delta.getY());                }
            }
        });

        byNode.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                byNode.setOpacity(1.0);
            }
        });
    }
    private void makeNormalizable(final Stage stage, final Node byNode) {
        byNode.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() >= 2) {
                changeMaximizeProperty(stage);
            }
        });
    }
    private void makeMinimizable(final Stage stage, final Node byNode) {
        byNode.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                stage.setIconified(true);
            }
        });
    }
    private void makeMaximizable(final Stage stage, final Node byNode) {
        byNode.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                changeMaximizeProperty(stage);
            }
        });
    }
    private void makeClosable(final Stage stage, final Node byNode) {
        byNode.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                stage.hide();
            }
        });
    }
    private void changeMaximizeProperty(Stage stage) {
        stage.setMaximized(!stage.isMaximized());
        if (stage.isMaximized()) {
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);

            AnchorPane.setLeftAnchor(body, 0.0);
            AnchorPane.setRightAnchor(body, 0.0);
            AnchorPane.setTopAnchor(body, 0.0);
            AnchorPane.setBottomAnchor(body, 0.0);
        } else {
            // Showing shadow when normalized.
            AnchorPane.setLeftAnchor(root, 5.0);
            AnchorPane.setRightAnchor(root, 5.0);
            AnchorPane.setTopAnchor(root, 5.0);
            AnchorPane.setBottomAnchor(root, 5.0);

            // Make offset for change the size of application via mouse.
            AnchorPane.setLeftAnchor(body, 2.0);
            AnchorPane.setRightAnchor(body, 2.0);
            AnchorPane.setTopAnchor(body, 2.0);
            AnchorPane.setBottomAnchor(body, 2.0);
        }
    }

    @FXML
    private void showHomeView() {
        switchIconBehaviour(Const.ANY);
        toggleNav(navPluginsWin, navPluginsMac);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Home.fxml"));
            contentPane.setCenter((AnchorPane)loader.load());
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @FXML
    private void showLesson() {
        switchIconBehaviour(Const.QUIZ);
        toggleNav(navPluginsWin, navPluginsMac);
        Main.getStage().fireEvent(new Event(getClass(), Main.getStage(), Const.EVENT_OPEN_LESSON));
    }

    @FXML
    private void showQuiz() {
        switchIconBehaviour(Const.LESSON);
        toggleNav(navPluginsWin, navPluginsMac);
        Main.getStage().fireEvent(new Event(getClass(), Main.getStage(), Const.EVENT_OPEN_QUIZ));
    }

    @FXML
    private void showAboutView() {
        switchIconBehaviour(Const.ANY);
        toggleNav(navAboutWin, navAboutMac);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/About.fxml"));
            contentPane.setCenter((AnchorPane)loader.load());
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    @FXML
    private void showSettingsView() {
        switchIconBehaviour(Const.ANY);
        toggleNav(navSettingsWin, navSettingsMac);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Settings.fxml"));
            contentPane.setCenter((AnchorPane)loader.load());
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @FXML
    private void eventNavMouseEntered(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Region) {
            Region region = (Region) mouseEvent.getSource();
            if (region.getOpacity() == 1.0) {
                // do nothing
            } else {
                region.setOpacity(0.9);
            }
        }
    }

    @FXML
    private void eventNavMouseExited(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Region) {
            Region region = (Region) mouseEvent.getSource();
            if (region.getOpacity() == 1.0) {
                // do nothing
            } else {
                region.setOpacity(0.75);
            }
        }
    }

    @FXML
    private void showControls() {
        ((SVGPath) sideCloseButton.getGraphic()).setFill(Paint.valueOf("rgb(35, 35, 35)"));
        ((SVGPath) sideMinimizeButton.getGraphic()).setFill(Paint.valueOf("rgb(35, 35, 35)"));
        ((SVGPath) sideMaximizeButton.getGraphic()).setFill(Paint.valueOf("rgb(35, 35, 35)"));
    }

    @FXML
    private void hideControls() {
        ((SVGPath) sideCloseButton.getGraphic()).setFill(Paint.valueOf("transparent"));
        ((SVGPath) sideMinimizeButton.getGraphic()).setFill(Paint.valueOf("transparent"));
        ((SVGPath) sideMaximizeButton.getGraphic()).setFill(Paint.valueOf("transparent"));
    }

    private void toggleNav(Region targetWin, Region targetMac) {
        Arrays.asList(navPluginsWin, navLessonWin, navQuizWin, navAboutWin, navSettingsWin, navPluginsMac, navLessonMac, navQuizMac, navAboutMac, navSettingsMac).forEach(region -> {
            if (region == targetWin || region == targetMac) {
                region.setOpacity(1.0);
            } else {
                region.setOpacity(0.75);
            }
        });
    }

    public void onEvent(EventType event) {
        //beaware the function is called two times
        if(event == Const.EVENT_APPLY_THEME){
            applyTheme();
        }else if(event == Const.EVENT_ANY_BEHAVIOUR){
            switchIconBehaviour(Const.ANY);
        }else if(event == Const.EVENT_QUIZ_BEHAVIOUR){
            switchIconBehaviour(Const.QUIZ);
        }
    }

    private void applyTheme() {
        if (Const.WIN.equals(Main.getConf().getTheme())) {
            body.setTop(head);
            body.setLeft(null);

            handPaneMac.setVisible(false);
        } else if (Const.MAC.equals(Main.getConf().getTheme())) {
            body.setTop(null);
            body.setLeft(sideArea);

            handPaneMac.setVisible(true);
        }
    }
    private void switchIconBehaviour(int opt) {
        switch (opt){
            case Const.ANY:
                navQuizWin.setVisible(false);
                navQuizMac.setVisible(false);
                navLessonWin.setVisible(false);
                navLessonMac.setVisible(false);
                break;
            case Const.LESSON:
                navLessonWin.setVisible(true);
                navLessonMac.setVisible(true);
                navQuizWin.setVisible(false);
                navQuizMac.setVisible(false);
                break;
            case Const.QUIZ:
                navLessonWin.setVisible(false);
                navLessonMac.setVisible(false);
                navQuizWin.setVisible(true);
                navQuizMac.setVisible(true);
                break;
        }
    }
    public void notifyFocusState(boolean isFocused) {
        if (isFocused) {
            sideCloseButton.setStyle("-fx-background-color: red; -fx-background-radius: 5em;");
            sideMinimizeButton.setStyle("-fx-background-color: orange; -fx-background-radius: 5em;");
            sideMaximizeButton.setStyle("-fx-background-color: #59bf53; -fx-background-radius: 5em;");
        } else {
            sideCloseButton.setStyle("-fx-background-color: #808080; -fx-background-radius: 5em;");
            sideMinimizeButton.setStyle("-fx-background-color: #808080; -fx-background-radius: 5em;");
            sideMaximizeButton.setStyle("-fx-background-color: #808080; -fx-background-radius: 5em;");
        }
    }
}
