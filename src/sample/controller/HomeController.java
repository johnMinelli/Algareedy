package sample.controller;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import sample.Main;
import sample.conf.Const;
import sample.model.Lesson;
import sample.model.Question;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane content;

    private static final String ITEM = "Lezione ";

    private int quiz = 0;
    private int answered = -1;

    private String pathRefresh = "M440.65 12.57l4 82.77A247.16 247.16 0 0 0 255.83 8C134.73 8 33.91 94.92 12.29 209.82A12 12 0 0 0 24.09 224h49.05a12 12 0 0 0 11.67-9.26 175.91 175.91 0 0 1 317-56.94l-101.46-4.86a12 12 0 0 0-12.57 12v47.41a12 12 0 0 0 12 12H500a12 12 0 0 0 12-12V12a12 12 0 0 0-12-12h-47.37a12 12 0 0 0-11.98 12.57zM255.83 432a175.61 175.61 0 0 1-146-77.8l101.8 4.87a12 12 0 0 0 12.57-12v-47.4a12 12 0 0 0-12-12H12a12 12 0 0 0-12 12V500a12 12 0 0 0 12 12h47.35a12 12 0 0 0 12-12.6l-4.15-82.57A247.17 247.17 0 0 0 255.83 504c121.11 0 221.93-86.92 243.55-201.82a12 12 0 0 0-11.8-14.18h-49.05a12 12 0 0 0-11.67 9.26A175.86 175.86 0 0 1 255.83 432z";
    private String pathCheck = "M173.898 439.404l-166.4-166.4c-9.997-9.997-9.997-26.206 0-36.204l36.203-36.204c9.997-9.998 26.207-9.998 36.204 0L192 312.69 432.095 72.596c9.997-9.997 26.207-9.997 36.204 0l36.203 36.204c9.997 9.997 9.997 26.206 0 36.204l-294.4 294.401c-9.998 9.997-26.207 9.997-36.204-.001z";
    private String pathBack = "M402.746 877.254l-320-320c-24.994-24.992-24.994-65.516 0-90.51l320-320c24.994-24.992 65.516-24.992 90.51 0 24.994 24.994 24.994 65.516 0 90.51l-210.746 210.746h613.49c35.346 0 64 28.654 64 64s-28.654 64-64 64h-613.49l210.746 210.746c12.496 12.496 18.744 28.876 18.744 45.254s-6.248 32.758-18.744 45.254c-24.994 24.994-65.516 24.994-90.51 0z";
    private String pathNext = "M190.5 66.9l22.2-22.2c9.4-9.4 24.6-9.4 33.9 0L441 239c9.4 9.4 9.4 24.6 0 33.9L246.6 467.3c-9.4 9.4-24.6 9.4-33.9 0l-22.2-22.2c-9.5-9.5-9.3-25 .4-34.3L311.4 296H24c-13.3 0-24-10.7-24-24v-32c0-13.3 10.7-24 24-24h287.4L190.9 101.2c-9.8-9.3-10-24.8-.4-34.3z";
    private String pathEnd = "M243.2 189.9V258c26.1 5.9 49.3 15.6 73.6 22.3v-68.2c-26-5.8-49.4-15.5-73.6-22.2zm223.3-123c-34.3 15.9-76.5 31.9-117 31.9C296 98.8 251.7 64 184.3 64c-25 0-47.3 4.4-68 12 2.8-7.3 4.1-15.2 3.6-23.6C118.1 24 94.8 1.2 66.3 0 34.3-1.3 8 24.3 8 56c0 19 9.5 35.8 24 45.9V488c0 13.3 10.7 24 24 24h16c13.3 0 24-10.7 24-24v-94.4c28.3-12.1 63.6-22.1 114.4-22.1 53.6 0 97.8 34.8 165.2 34.8 48.2 0 86.7-16.3 122.5-40.9 8.7-6 13.8-15.8 13.8-26.4V95.9c.1-23.3-24.2-38.8-45.4-29zM169.6 325.5c-25.8 2.7-50 8.2-73.6 16.6v-70.5c26.2-9.3 47.5-15 73.6-17.4zM464 191c-23.6 9.8-46.3 19.5-73.6 23.9V286c24.8-3.4 51.4-11.8 73.6-26v70.5c-25.1 16.1-48.5 24.7-73.6 27.1V286c-27 3.7-47.9 1.5-73.6-5.6v67.4c-23.9-7.4-47.3-16.7-73.6-21.3V258c-19.7-4.4-40.8-6.8-73.6-3.8v-70c-22.4 3.1-44.6 10.2-73.6 20.9v-70.5c33.2-12.2 50.1-19.8 73.6-22v71.6c27-3.7 48.4-1.3 73.6 5.7v-67.4c23.7 7.4 47.2 16.7 73.6 21.3v68.4c23.7 5.3 47.6 6.9 73.6 2.7V143c27-4.8 52.3-13.6 73.6-22.5z";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        content.getChildren().clear();
        quiz=0;
        answered = -1;
        JFXListView<Label> list = new JFXListView<>();
        for (int i = 0; i < Main.getLessons().length; i++) {
            list.getItems().add(new Label(ITEM + i + " - " + Main.getLesson(i).getTitle()));
        }
        list.getStyleClass().add("mylistview");
        list.depthProperty().set(1);
        list.setExpanded(true);
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
        if(!l.hasAlgorithm()){
            JFXScrollPane pane = new JFXScrollPane();
            //pane.setPrefWidth(Main.getConf().getWidth()-((Main.getConf().getTheme().equals(Const.WIN))?Const.BAR_WIN_WIDTH:Const.BAR_MAC_WIDTH));
            String title = l.getTitle();
            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-text-fill:WHITE; -fx-font-size: 30;");
            JFXButton button = new JFXButton("");
            SVGGlyph arrow = new SVGGlyph(0, "back", pathBack, Color.WHITE);
            arrow.setSize(20, 16);
            button.setGraphic(arrow);
            button.setRipplerFill(Color.WHITE);
            pane.getBottomBar().getChildren().add(titleLabel);
            pane.getTopBar().getChildren().add(button);
            pane.getTopBar().setAlignment(Pos.TOP_RIGHT);
            button.setOnMouseClicked(subMouseEvent -> {
                if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                    Main.getStage().fireEvent(new Event(getClass(), Main.getStage(), Const.EVENT_ANY_BEHAVIOUR));
                    initialize(null, null);
                }
            });
            if (l.isXML()) {
                try {
                    String fxml = l.getTheory();
                    InputStream targetStream = new ByteArrayInputStream(fxml.getBytes());
                    FXMLLoader loader = new FXMLLoader();
                    pane.setContent((StackPane) loader.load(targetStream));
                } catch (Exception IO) {
                    System.out.println("Lesson XML Transformer Exception");
                }
            } else {
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setHeading(new Label(l.getSubtitle()));
                layout.setBody(new Label(l.getTheory()));
                StackPane container = new StackPane(layout);
                container.setPadding(new Insets(24));
                pane.setContent(container);
            }
            content.getChildren().add(pane);
            AnchorPane.setLeftAnchor(pane,0.0);AnchorPane.setRightAnchor(pane,0.0);AnchorPane.setBottomAnchor(pane,0.0);AnchorPane.setTopAnchor(pane,0.0);
        }else{
            if(l.getAlgorithm().equals("change")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ChangeMaking.fxml"));
                try {
                    content.getChildren().add((AnchorPane)loader.load());
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void openQuiz() {
        Lesson l = Main.getLesson();
        content.getChildren().clear();
        //setting head layout
        JFXDialogLayout layout = new JFXDialogLayout();
        String title = l.getTitle();
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill:BLACK; -fx-font-size: 30;");
        JFXButton refreshBtn = new JFXButton("");
        SVGGlyph refreshG = new SVGGlyph(0, "refresh", pathRefresh, Color.BLACK);
        SVGGlyph checkG = new SVGGlyph(0, "check", pathCheck, Color.BLACK);
        SVGGlyph nextG = new SVGGlyph(0, "next", pathNext, Color.BLACK);
        SVGGlyph endG = new SVGGlyph(0, "end", pathEnd, Color.BLACK);
        refreshG.setSizeForHeight(20);checkG.setSizeForHeight(15);nextG.setSizeForHeight(15);endG.setSizeForHeight(18);
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
        //bar.setMinWidth(Const.PANE_WIDTH-((Main.getConf().getTheme().equals(Const.WIN))?Const.BAR_WIN_WIDTH:Const.BAR_MAC_WIDTH));
        JFXButton nextBtn = new JFXButton("");
        nextBtn.setButtonType(JFXButton.ButtonType.RAISED);
        nextBtn.getStyleClass().add("animated-option-button");
        JFXButton hintBtn = new JFXButton("HINT");
        hintBtn.setStyle("-fx-background-color: #5264AE;-fx-text-fill: WHITE");
        hintBtn.setVisible(false);
        if(quiz>0||answered>=0)    refreshBtn.setDisable(false);
        if (quiz<l.getQuiz().length) {
            Question q = l.getQuiz()[quiz];
            Label questionLabel = new Label(q.getText());
            questionLabel.setStyle("-fx-text-fill:BLACK; -fx-font-size: 18;");
            final ToggleGroup group = new ToggleGroup();
            group.setUserData(q.getCorrect());
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.getChildren().add(questionLabel);
            for (int i = 0; i < q.getOptions().length; i++) {
                JFXRadioButton radio = new JFXRadioButton(q.getOptions()[i]);
                radio.setPadding(new Insets(10));
                radio.setToggleGroup(group);
                radio.setUserData(i);
                vbox.getChildren().add(radio);
            }
            bar.setProgress(Double.valueOf(quiz)/Double.valueOf(Math.max(l.getQuiz().length,1)));
            layout.setBody(vbox);
            //alert
            hintBtn.setOnMouseClicked(subMouseEvent -> {
                if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                    JFXDialogLayout alertLayout = new JFXDialogLayout();
                    alertLayout.setBody(new Label(q.getHint()));
                    JFXAlert<Void> alert = new JFXAlert<>(Main.getStage());
                    alert.setOverlayClose(true);
                    alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert.initModality(Modality.NONE);
                    alert.setContent(alertLayout);
                    alert.showAndWait();
                }
            });
            //next button
            if(answered>=0){
                nextBtn.setGraphic(nextG);
                hintBtn.setVisible(true);
                group.selectToggle(group.getToggles().get(answered));
                ((JFXRadioButton)group.getToggles().get(answered)).getStyleClass().add("custom-jfx-radio-button-red");
                ((JFXRadioButton)group.getToggles().get((int) group.getUserData())).getStyleClass().add("custom-jfx-radio-button-green");
            }else{
                nextBtn.setGraphic(checkG);
            }
            nextBtn.setOnMouseClicked(subMouseEvent -> {
                if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                    if(answered>=0) {
                        quiz++;
                        answered = -1;
                        openQuiz();
                    }else {
                        nextBtn.setGraphic(nextG);
                        refreshBtn.setDisable(false);
                        hintBtn.setVisible(true);
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
            nextBtn.setGraphic(endG);
            nextBtn.setOnMouseClicked(subMouseEvent -> {
                if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                    initialize(null,null);
                }
            });
            Label endLabel = new Label("Test di autocomprensione terminato");
            endLabel.setStyle("-fx-text-fill:BLACK; -fx-font-size: 18;");
            layout.setBody(endLabel);
        }

        StackPane container = new StackPane(layout);
        container.setPadding(new Insets(24));
        content.getChildren().add(container);
        AnchorPane.setLeftAnchor(container,0.0);
        AnchorPane.setRightAnchor(container,0.0);
        content.getChildren().add(bar);
        AnchorPane.setLeftAnchor(bar,0.0);
        AnchorPane.setRightAnchor(bar,0.0);
        AnchorPane.setBottomAnchor(bar,1.0);
        content.getChildren().add(refreshBtn);
        AnchorPane.setTopAnchor(refreshBtn,10.0);
        AnchorPane.setRightAnchor(refreshBtn,10.0);
        content.getChildren().add(nextBtn);
        AnchorPane.setBottomAnchor(nextBtn,24.0);
        AnchorPane.setRightAnchor(nextBtn,24.0);
        content.getChildren().add(hintBtn);
        AnchorPane.setBottomAnchor(hintBtn,29.0);
        AnchorPane.setLeftAnchor(hintBtn,24.0);
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
