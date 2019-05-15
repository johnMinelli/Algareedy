package sample.controller;



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.svg.SVGGlyph;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import sample.Main;
import sample.model.Lesson;
import sample.model.Question;


import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import org.xml.sax.InputSource;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HomeController implements Initializable {

	@FXML
	private AnchorPane content;

    private static final String ITEM = "Lezione ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        content.getChildren().clear();
        JFXListView<Label> list = new JFXListView<>();
        for (int i = 0; i < Main.getLessons().length; i++) {
            list.getItems().add(new Label(ITEM + i +" - "+Main.getLessons(i).getTitolo()));
        }
        list.depthProperty().set(1);
        list.depthProperty().set(1);
        list.setExpanded(true);

        content.getChildren().add(list);
        AnchorPane.setLeftAnchor(list, 40.0);


        list.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Lesson l =  Main.getLessons(list.getSelectionModel().getSelectedIndices().get(0));
                //open lesson
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
                if(l.isXML()) {
                    try {
                        /*
                        //Node a = readxml();
                        String fxml = "<StackPane>" +
                                "<children>" +
                                "<HBox alignment=\"CENTER\">" +
                                "<children>" +
                                "<Label text=\"All rights rightly right\" />" +
                                "</children>" +
                                "</HBox>" +
                                "</children>" +
                                "</StackPane>";
                         */
                        String fxml = l.getStuff();
                        InputStream targetStream = new ByteArrayInputStream(fxml.getBytes());
                        FXMLLoader loader = new FXMLLoader();
                        pane.setContent((StackPane) loader.load(targetStream));
                    } catch (Exception IO) {
                        //no
                    }
                }else{
                    JFXDialogLayout layout = new JFXDialogLayout();
                    layout.setHeading(new Label("Modal Dialog using JFXAlert"));
                    layout.setBody(new Label(l.getStuff()));
                    StackPane container = new StackPane(layout);
                    container.setPadding(new Insets(24));
                    pane.setContent(container);
                }



                content.getChildren().add(pane);
            }
        });
    }





}
