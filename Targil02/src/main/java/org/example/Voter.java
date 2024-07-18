package org.example;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

public class Voter {
    private String name;
    private Map<String, String> answers;

    public Voter () {
        Faker faker = new Faker();
        this.name = faker.name().fullName();
        this.answers = new HashMap<>();
    }

    public String toString () {
        return "Name: " + this.name + (!this.hasVoted() ? "" : ", answers: " + this.answers);
    }

    public boolean hasVoted () {
        return !this.answers.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void updateAnswer (String question, String answer) {
        this.answers.put(question, answer);
    }

    public String getAnswerForQuestion (String question) {
        return this.answers.get(question);
    }
}
