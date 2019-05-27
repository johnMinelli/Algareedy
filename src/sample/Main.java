package sample;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sample.conf.Const;
import sample.controller.Controller;
import sample.conf.Configuration;
import sample.model.Lesson;
import sample.model.Question;
import sample.model.Seeker;
import sample.model.Vector2D;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sample.conf.Const.*;
import sample.model.ChangeMakingCode;

public class Main extends Application {
    private static Stage stage;

    private static Parent app;

    private static Configuration conf;

    private static Lesson[] lessons;

    private static int lesson;

    private static Seeker[] seekingLabels;

    private Controller appController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.conf = new Configuration();
        try {loadLessons();}catch (Exception e){return;}
        loadLabels();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/Sample.fxml"));
        app = fxmlLoader.load();
        appController = fxmlLoader.getController();
        appController.initialize();

        stage.setTitle(Const.TITLE);
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
        stage.setWidth(conf.getWidth());
        stage.setHeight(conf.getHeight());
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            appController.notifyFocusState(newValue);
        });
        stage.show();
    }

/* Getter and Setter*/
    public static Stage getStage() {
        return stage;
    }
    public static Parent getParent() {
        return app;
    }
    public static Configuration getConf() {
        return conf;
    }
    public static Lesson[] getLessons() {
        return lessons;
    }
    public static Lesson getLesson(int i) { return lessons[i]; }
    public static Lesson getLesson() { return lessons[lesson]; }
    public static void setLesson(int view) { lesson = view; }
    public static Seeker[] getSeekers() { return seekingLabels; }
    public static Seeker getSeeker(int i) { return seekingLabels[i]; }
/*------------------*/

    private void loadLabels() {
        //add labels
        seekingLabels = new Seeker[Const.SPRITE_LIST.length];
        for(int i = 0; i < Const.SPRITE_LIST.length; i++) {
            // random location
            Random r = new Random();
            double x = r.nextDouble() * conf.getWidth()/2+(conf.getWidth()/4);
            double y = r.nextDouble() * conf.getHeight()/2+(conf.getHeight()/10);

            // dimensions
            double size = 50*r.nextDouble()+10;

            // create vehicle data
            Vector2D location = new Vector2D( x,y);
            Vector2D velocity = new Vector2D( 0,0);
            Vector2D acceleration = new Vector2D( 0,0);

            // create sprite
            seekingLabels[i] = new Seeker(Const.SPRITE_LIST[i],location, velocity, acceleration, size);
        }

    }
    public static void loadLessons() throws Exception {
        try {
            File fXmlFile = new File(FILENAME);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Lezione");
            lessons = new Lesson[nList.getLength()];
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item (i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String titolo = eElement.getElementsByTagName("Titolo").item(0).getTextContent();
                    String sottotitolo = (eElement.getElementsByTagName("Sottotitolo").getLength()!=0)?eElement.getElementsByTagName("Sottotitolo").item(0).getTextContent():"";    //opzionale
                    String algoritmo = "";      //opzionale
                    boolean isXML = (((Element)eElement.getElementsByTagName("Teoria").item(0)).getAttribute("fxml").equals("true"))?true:false;
                    String teoria = (isXML)?
                            nodeToString(((Element) eElement.getElementsByTagName("Teoria").item(0)).getElementsByTagName("StackPane").item(0)) :
                            eElement.getElementsByTagName("Teoria").item(0).getTextContent();
                    if(eElement.getElementsByTagName("Algoritmo").getLength() != 0){
                        algoritmo = ((Element)eElement.getElementsByTagName("Algoritmo").item(0)).getAttribute("type");
                    }
                    //ChangeMakingCode cmc = new ChangeMakingCode();
                    //cmc.changeMaking(75);
                    NodeList qList = eElement.getElementsByTagName("Domanda");
                    Question[] quiz = new Question[qList.getLength()];
                    for (int j = 0; j < qList.getLength(); j++) {
                        Node qNode = qList.item(j);
                        if (qNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element qElement = (Element) qNode;
                            String text = qElement.getElementsByTagName("Testo").item(0).getTextContent();
                            String hint = qElement.getElementsByTagName("Suggerimento").item(0).getTextContent();
                            NodeList rList = qElement.getElementsByTagName("Opzione");
                            String[] options = new String[rList.getLength()];
                            int correct = -1;
                            for (int k = 0; k < rList.getLength(); k++) {
                                Node rNode = rList.item(k);
                                if (rNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element rElement = (Element) rNode;
                                    options[k] = rElement.getTextContent();
                                    if (rElement.getAttribute("corretta").equals("true")) {
                                        correct = k;
                                    }
                                }
                            }
                            if(correct == -1)throw new Exception("Risposta corretta non impostata");
                            quiz[j] = new Question(text, hint, options, correct);
                        }
                    }
                    lessons[i] = new Lesson(titolo, sottotitolo, teoria, quiz, isXML, algoritmo);
                }
            }
        } catch (Exception e) {
            stage.setScene(new Scene(new Region(), Color.TRANSPARENT));
            stage.setOpacity(0.0);
            stage.show();
            JFXDialogLayout alertLayout = new JFXDialogLayout();
            alertLayout.setPrefHeight(400.0);
            alertLayout.setHeading(new Label("File xml non trovato o non formattato corretamente."));
            JFXTextArea area = new JFXTextArea("Assicurarsi che si trovi nella path relativa ./lessons.xml e che sia\n formattato come segue:\n" +
                    "<root>\n" +
                    "<Lezione>\n" +
                    "\t<Titolo>...</Titolo>\n" +
                    "\t<Algoritmo type=\"change\"/>\n" +
                    "\t<Teoria fxml=\"false\" />\n" +
                    "\t<Quiz>\n" +
                    "\t\t<Domanda>\n" +
                    "\t\t\t<Testo>...?</Testo>\n" +
                    "\t\t\t<Suggerimento>!!!</Suggerimento>\n" +
                    "\t\t\t<Opzione corretta=\"true\">.</Opzione>\n" +
                    "\t\t</Domanda>\n" +
                    "\t</Quiz>\n" +
                    "</Lezione>\n" +
                    "<root>");
            area.setEditable(false);
            alertLayout.setBody(area);
            JFXAlert<Void> alert = new JFXAlert<>(stage);
            alert.setOverlayClose(true);
            alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
            alert.initModality(Modality.NONE);
            alert.setContent(alertLayout);
            alert.showAndWait();
            alert.setOnCloseRequest(event -> stage.close());
            throw new Exception("Exit");
        }
    }
    private static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString().replaceAll("(\\t)|(\\r)|(\\n)", "");
    }
}
