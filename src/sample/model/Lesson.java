package sample.model;

public class Lesson {
    private String titolo;
    private String stuff;
    private Question[] quiz;
    private boolean isXML;

    public Lesson(String titolo, String stuff, Question[] quiz, boolean isXML) {
        this.titolo = titolo;
        this.stuff = stuff;
        this.quiz = quiz;
        this.isXML = isXML;
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
}
