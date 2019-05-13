package sample.model;

public class Lesson {
    private String titolo;
    private String stuff;
    private Question[] quiz;

    public Lesson(String titolo, String stuff, Question[] quiz) {
        this.titolo = titolo;
        this.stuff = stuff;
        this.quiz = quiz;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getStuff() {
        return stuff;
    }

    public Question[] getQuiz() {
        return quiz;
    }
}
