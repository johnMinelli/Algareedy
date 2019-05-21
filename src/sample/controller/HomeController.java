package sample.controller;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.Main;
import sample.conf.Const;
import sample.model.Lesson;
import sample.model.Question;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane content;

    private static final String ITEM = "Lezione ";

    private int quiz = 0;
    private int answered = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        content.getChildren().clear();
        quiz=0;
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
                if(Main.getLesson().getQuiz().length>0) {
                    Main.getStage().fireEvent(new Event(getClass(), Main.getStage(), Const.EVENT_QUIZ_BEHAVIOUR));
                }
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
        //setting head layout
        JFXDialogLayout layout = new JFXDialogLayout();
        String title = l.getTitolo();
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill:BLACK; -fx-font-size: 30;");
        JFXButton refreshBtn = new JFXButton("");
        SVGGlyph refreshG = new SVGGlyph(0,
                "refresh",
                "M440.65 12.57l4 82.77A247.16 247.16 0 0 0 255.83 8C134.73 8 33.91 94.92 12.29 209.82A12 12 0 0 0 24.09 224h49.05a12 12 0 0 0 11.67-9.26 175.91 175.91 0 0 1 317-56.94l-101.46-4.86a12 12 0 0 0-12.57 12v47.41a12 12 0 0 0 12 12H500a12 12 0 0 0 12-12V12a12 12 0 0 0-12-12h-47.37a12 12 0 0 0-11.98 12.57zM255.83 432a175.61 175.61 0 0 1-146-77.8l101.8 4.87a12 12 0 0 0 12.57-12v-47.4a12 12 0 0 0-12-12H12a12 12 0 0 0-12 12V500a12 12 0 0 0 12 12h47.35a12 12 0 0 0 12-12.6l-4.15-82.57A247.17 247.17 0 0 0 255.83 504c121.11 0 221.93-86.92 243.55-201.82a12 12 0 0 0-11.8-14.18h-49.05a12 12 0 0 0-11.67 9.26A175.86 175.86 0 0 1 255.83 432z",
                Color.BLACK);
        refreshG.setSize(20, 20);
        refreshBtn.setButtonType(JFXButton.ButtonType.RAISED);
        refreshBtn.setGraphic(refreshG);
        refreshBtn.setRipplerFill(Color.WHITE);
        refreshBtn.setDisable(true);
        refreshBtn.setOnMouseClicked(subMouseEvent -> {
            if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                quiz = 0;
                answered = -1;
                openQuiz();
            }
        });
        layout.setHeading(titleLabel);
        //set body layout
        JFXProgressBar bar = new JFXProgressBar();
        bar.setMinWidth(Const.PANE_WIDTH-((Main.getConf().getTheme().equals(Const.WIN))?Const.BAR_WIN_WIDTH:Const.BAR_MAC_WIDTH));
        JFXButton nextBtn = new JFXButton("");
        nextBtn.setButtonType(JFXButton.ButtonType.RAISED);
        nextBtn.getStyleClass().add("animated-option-button");
        if(quiz>0||answered>=0)    refreshBtn.setDisable(false);
        if (quiz<l.getQuiz().length) {
            Question q = l.getQuiz()[quiz];
            final ToggleGroup group = new ToggleGroup();
            group.setUserData(q.getCorrect());
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            for (int i = 0; i < q.getOptions().length; i++) {
                JFXRadioButton radio = new JFXRadioButton(q.getOptions()[i]);
                radio.setPadding(new Insets(10));
                radio.setToggleGroup(group);
                radio.setUserData(i);
                vbox.getChildren().add(radio);
            }

            bar.setProgress(Double.valueOf(quiz)/Double.valueOf(Math.max(l.getQuiz().length,1)));
            layout.setBody(vbox);
            //next button
            if(answered>=0){
                nextBtn.setText("->");
                group.selectToggle(group.getToggles().get(answered));
                ((JFXRadioButton)group.getToggles().get(answered)).getStyleClass().add("custom-jfx-radio-button-red");
                ((JFXRadioButton)group.getToggles().get((int) group.getUserData())).getStyleClass().add("custom-jfx-radio-button-green");
            }else{
                nextBtn.setText("✓");
            }
            nextBtn.setOnMouseClicked(subMouseEvent -> {
                if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                    if(answered>=0) {
                        quiz++;
                        answered = -1;
                        openQuiz();
                    }else {
                        nextBtn.setText("->");
                        refreshBtn.setDisable(false);
                        //hint.setVisible(true);
                        JFXRadioButton sel = (JFXRadioButton) group.getSelectedToggle();
                        if(sel != null){
                            sel.getStyleClass().add("custom-jfx-radio-button-red");
                            answered = (int) sel.getUserData();
                        }else{
                            answered = (int) group.getUserData();
                        }
                        ((JFXRadioButton)group.getToggles().get((int) group.getUserData())).getStyleClass().add("custom-jfx-radio-button-green");
                    }
                }
            });
        }else if (quiz!=0 && quiz == l.getQuiz().length){
            //quiz ended
            bar.setProgress(1.0);
            nextBtn.setText("YAY");
            nextBtn.setOnMouseClicked(subMouseEvent -> {
                if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                    //torna al menu
                }
            });
        }
        StackPane container = new StackPane(layout);
        container.setPadding(new Insets(24));
        content.getChildren().add(container);
        content.getChildren().add(bar);
        AnchorPane.setBottomAnchor(bar,1.0);
        content.getChildren().add(refreshBtn);
        AnchorPane.setTopAnchor(refreshBtn,10.0);
        AnchorPane.setRightAnchor(refreshBtn,10.0);
        content.getChildren().add(nextBtn);
        AnchorPane.setBottomAnchor(nextBtn,24.0);
        AnchorPane.setRightAnchor(nextBtn,24.0);

        /*
        HBox hbox = new HBox();
            hbox.getChildren().add(vbox);
            hbox.setSpacing(50);
            hbox.setPadding(new Insets(40, 10, 10, 120));
         */
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
