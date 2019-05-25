package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
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
        loadLessons();
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
    public static void loadLessons(){
        try {
            File fXmlFile = new File(FILENAME);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Lezione");
            lessons = new Lesson[nList.getLength()];
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String titolo = eElement.getElementsByTagName("Titolo").item(0).getTextContent();
                    boolean isXML = (((Element)eElement.getElementsByTagName("Stuff").item(0)).getAttribute("fxml").equals("true"))?true:false;
                    String stuff = (isXML)?
                            nodeToString(((Element) eElement.getElementsByTagName("Stuff").item(0)).getElementsByTagName("StackPane").item(0)) :
                            eElement.getElementsByTagName("Stuff").item(0).getTextContent();
                    if(eElement.getElementsByTagName("Algoritmo").getLength() == 0) System.out.println("Do something here");
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
                            int correct = 0;
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
                            quiz[j] = new Question(text, hint, options, correct);
                        }
                    }
                    lessons[i] = new Lesson(titolo, stuff, quiz, isXML);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
