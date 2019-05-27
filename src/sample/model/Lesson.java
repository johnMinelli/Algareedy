package sample.model;

public class Lesson {
    private String title;
    private String subtitle;
    private String theory;
    private Question[] quiz;
    private boolean isXML;
    private String algorithm;

    public Lesson(String title, String subtitle, String theory, Question[] quiz, boolean isXML, String algorithm) {
        this.title = title;
        this.subtitle = subtitle;
        this.theory = theory;
        this.quiz = quiz;
        this.isXML = isXML;
        this.algorithm = algorithm;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTheory() {
        if(isXML){
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<?import javafx.scene.layout.StackPane?>" +
                    "<?import javafx.scene.layout.VBox?>" +
                    "<?import javafx.scene.control.Label?>" +
                    "<?import com.jfoenix.controls.*?>" +
                    "<?import com.jfoenix.controls.JFXListView?>" +
                    "<?import javafx.geometry.Insets?>" +
                    "<?import javafx.scene.layout.*?>" +
                    theory;
        }else{
            return theory;
        }

    }

    public Question[] getQuiz() {
        return quiz;
    }

    public boolean isXML() {
        return isXML;
    }
    
    public boolean hasAlgorithm() {
        return !algorithm.equals("");
    }

    public String getAlgorithm() {
        return algorithm;
    }
    
    
}
