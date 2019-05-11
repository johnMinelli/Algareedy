package sample.model;

public class Question {
    private String text;
    private String[] options;
    private int correct;

    public Question(String text, String[] options, int correct) {
        this.text = text;
        this.options = options;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrect() {
        return correct;
    }
}
