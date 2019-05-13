package sample.controller;



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.svg.SVGGlyph;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Main;
import sample.model.Lesson;
import sample.model.Question;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

	@FXML
	private AnchorPane content;

    private static final String ITEM = "Lezione ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        content.getChildren().clear();
        Lesson[] lessons = Main.getLessons();
        JFXListView<Label> list = new JFXListView<>();
        for (int i = 0; i < lessons.length; i++) {
            list.getItems().add(new Label(ITEM + i +" - "+lessons[i].getTitolo()));
        }
        list.depthProperty().set(1);
        list.depthProperty().set(1);
        list.setExpanded(true);

        content.getChildren().add(list);
        AnchorPane.setLeftAnchor(list, 20.0);


        list.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                String title = Main.getLessons(list.getSelectionModel().getSelectedIndices().get(0)).getTitolo();
                JFXListView<Label> lister = new JFXListView<>();
                for (int i = 0; i < 100; i++) {
                    lister.getItems().add(new Label("Item " + i));
                }
                lister.getStyleClass().add("mylistview");
                lister.setMaxHeight(3400);


                StackPane container = new StackPane(lister);
                container.setPadding(new Insets(24));

                JFXScrollPane pane = new JFXScrollPane();
                pane.setContent(container);

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
                pane.getTopBar().getChildren().add(button);

                button.setOnMouseClicked(subMouseEvent -> {
                    if (subMouseEvent.getButton() == MouseButton.PRIMARY) {
                        this.initialize(null,null);
                    }
                });

                Label titleLabel = new Label(title);
                pane.getBottomBar().getChildren().add(titleLabel);
                titleLabel.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");
                JFXScrollPane.smoothScrolling((ScrollPane) pane.getChildren().get(0));
                content.getChildren().clear();
                content.getChildren().add(pane);

                //try{
                    //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Settings.fxml"));
                    //content.getChildren().add((AnchorPane)loader.load());
                //}catch (IOException io){
                    //io.printStackTrace();
                //}
            }
        });
    }

}
