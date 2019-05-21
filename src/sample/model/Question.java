package sample.model;

public class Question {
    private String text;
    private String hint;
    private String[] options;
    private int correct;

    public Question(String text, String hint, String[] options, int correct) {
        this.text = text;
        this.hint = hint;
        this.options = options;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public String getHint() {
        return hint;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrect() {
        return correct;
    }

}
