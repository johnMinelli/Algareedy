package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.svg.SVGGlyph;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.Main;
import sample.conf.Const;
import sample.model.Lesson;
import sample.model.Question;


import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane content;

    private static final String ITEM = "Lezione ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        content.getChildren().clear();
        JFXListView<Label> list = new JFXListView<>();
        for (int i = 0; i < Main.getLessons().length; i++) {
            list.getItems().add(new Label(ITEM + i + " - " + Main.getLesson(i).getTitolo()));
        }
        list.getStyleClass().add("mylistview");
        list.depthProperty().set(1);
        list.setExpanded(true);
        //StackPane.setMargin(pane, new Insets(20, 0, 0, 20));
        AnchorPane.setTopAnchor(list, 20.0);
        AnchorPane.setLeftAnchor(list, 40.0);
        AnchorPane.setRightAnchor(list, 40.0);
        content.getChildren().add(list);


        list.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && !list.getSelectionModel().getSelectedIndices().isEmpty()) {
                Main.setLesson(list.getSelectionModel().getSelectedIndices().get(0));
                Main.getStage().fireEvent(new Event(getClass(), Main.getStage(), Const.EVENT_QUIZ_BEHAVIOUR));
                openLesson();
            }
        });
        Main.getStage().addEventHandler(Const.EVENT_ALL, event -> {onEvent(event.getEventType());});
    }

    public void openLesson() {
        Lesson l = Main.getLesson();
        content.getChildren().clear();
        JFXScrollPane pane = new JFXScrollPane();
        String title = l.getTitolo();
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");
        JFXButton button = new JFXButton("");
        SVGGlyph arrow = new SVGGlyph(0,
                "FULLSCREEN",
                "M402.746 877.254l-320-320c-24.994-24.992-24.994-65.516 0-90.51l320-320c24.994-24.992 65.516-24.992 90.51 0 24.994 24.994 "
                        + "24.994 65.516 0 90.51l-210.746 210.746h613.49c35.346 0 64 28.654 64 64s-28.654 64-64 64h-613.49l210.746 210.746c12.496 "
                        + "12.496 18.744 28.876 18.744 45.254s-6.248 32.758-18.744 45.254c-24.994 24.994-65.516 24.994-90.51 0z",
                Color.WHITE);
        arrow.setSize(20, 16);
        button.setGraphic(arrow);
        button.setRipplerFill(Color.WHITE);
        pane.getBottomBar().getChildren().add(titleLabel);
        pane.getTopBar().getChildren().add(button);
        button.setOnMouseClicked(subMouseEvent -> {
            if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                Main.getStage().fireEvent(new Event(getClass(), Main.getStage(), Const.EVENT_ANY_BEHAVIOUR));
                initialize(null, null);
            }
        });
        if (l.isXML()) {
            try {
                String fxml = l.getStuff();
                InputStream targetStream = new ByteArrayInputStream(fxml.getBytes());
                FXMLLoader loader = new FXMLLoader();
                pane.setContent((StackPane) loader.load(targetStream));
            } catch (Exception IO) {
                System.out.println("Lesson XML Transformer Exception");
            }
        } else {
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Label("Lesson subtitle"));
            layout.setBody(new Label(l.getStuff()));
            StackPane container = new StackPane(layout);
            container.setPadding(new Insets(24));
            pane.setContent(container);
        }
        content.getChildren().add(pane);
    }

    public void openQuiz() {
        Lesson l = Main.getLesson();
        content.getChildren().clear();
        JFXScrollPane pane = new JFXScrollPane();
        String title = l.getTitolo();
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");
        JFXButton button = new JFXButton("");
        SVGGlyph arrow = new SVGGlyph(0,
                "FULLSCREEN",
                "M402.746 877.254l-320-320c-24.994-24.992-24.994-65.516 0-90.51l320-320c24.994-24.992 65.516-24.992 90.51 0 24.994 24.994 "
                        + "24.994 65.516 0 90.51l-210.746 210.746h613.49c35.346 0 64 28.654 64 64s-28.654 64-64 64h-613.49l210.746 210.746c12.496 "
                        + "12.496 18.744 28.876 18.744 45.254s-6.248 32.758-18.744 45.254c-24.994 24.994-65.516 24.994-90.51 0z",
                Color.WHITE);
        arrow.setSize(20, 16);
        button.setGraphic(arrow);
        button.setRipplerFill(Color.WHITE);
        pane.getBottomBar().getChildren().add(titleLabel);
        pane.getTopBar().getChildren().add(button);
        button.setOnMouseClicked(subMouseEvent -> {
            if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                initialize(null, null);
            }
        });
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Lesson subtitle"));
        layout.setBody(new Label(l.getStuff()));
        StackPane container = new StackPane(layout);
        container.setPadding(new Insets(24));
        pane.setContent(container);
        content.getChildren().add(pane);
    }

    public void onEvent(EventType event) {
        //beaware the function is called two times
        if(event == Const.EVENT_OPEN_LESSON){
            openLesson();
        }else if(event == Const.EVENT_OPEN_QUIZ){
            openQuiz();
        }
    }





}
