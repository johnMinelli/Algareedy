package sample.model;

public class Lesson {
    private String titolo;
    private String stuff;
    private Question[] quiz;
    private boolean isXML;
    private String algorithm;

    public Lesson(String titolo, String stuff, Question[] quiz, boolean isXML, String algorithm) {
        this.titolo = titolo;
        this.stuff = stuff;
        this.quiz = quiz;
        this.isXML = isXML;
        this.algorithm = algorithm;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getStuff() {
        if(isXML){
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<?import javafx.scene.layout.StackPane?>" +
                    "<?import javafx.scene.layout.HBox?>" +
                    "<?import javafx.scene.control.Label?>" +
                    stuff;
        }else{
            return stuff;
        }

    }

    public Question[] getQuiz() {
        return quiz;
    }

    public boolean isXML() {
        return isXML;
    }
    
    public boolean hasAlgorithm() {
        return !this.algorithm.equals("");
    }

    public String getAlgorithm() {
        return algorithm;
    }
    
    
}
