package org.example;

import java.util.List;

public class Question {
    private String text;
    private List<String> options;

    public Question (String text, List<String> options) {
        this.text = text;
        this.options = options;
    }

    public String toString () {
        StringBuilder stringBuilder = new StringBuilder("Question: " + this.text);
        stringBuilder.append("\nOptions:\n");
        for (String option : this.options) {
            stringBuilder.append(option).append("\n");
        }
        return stringBuilder.toString();
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }
}
