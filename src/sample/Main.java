package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sample.controller.Controller;
import sample.conf.Configuration;
import sample.model.Lesson;
import sample.model.Question;

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

import static sample.conf.Const.*;

public class Main extends Application {
    private static Stage stage;

    private static Parent app;

    private static Configuration conf;

    private static Lesson[] lessons;

    private Controller appController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.conf = new Configuration();
        loadLessons();

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
        stage.setWidth(PANE_WIDTH);
        stage.setHeight(PANE_HEIGHT);
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
    public static Lesson getLessons(int i) {
        return lessons[i];
    }
    public static Lesson[] getLessons() {
        return lessons;
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
                    lessons[i] = new Lesson(titolo,stuff,isXML);
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
