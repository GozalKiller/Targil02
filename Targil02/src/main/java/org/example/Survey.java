package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Survey {
    private String name;
    private List<Question> questionList;
    private List<Voter> voters;

    public Survey(String name, List<Question> questionList) {
        this.name = name;
        this.questionList = questionList;
        this.generateVoters();
        printVotedVoters ();
        simulate ();
    }

    private void generateVoters () {
        this.voters = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            this.voters.add(new Voter());
        }
    }

    public String toString () {
        StringBuilder stringBuilder = new StringBuilder("Survey: ").append(this.name).append("\n\n");
        for (int i = 0; i < this.questionList.size(); i++) {
            stringBuilder.append("Question number ").append(i + 1).append(": \n").append(this.questionList.get(i)).append("\n");
        }
        stringBuilder.append("\nVoters List: \n");
        for (int i = 0; i < this.voters.size(); i++) {
            stringBuilder.append(i + 1).append(": ").append(this.voters.get(i)).append("\n");
        }
        return stringBuilder.toString();
    }

    private void printVotedVoters () {
        new Thread(() -> {
            while (true) {
                List<String> votedVoters =
                        this.voters
                                .stream()
                                .filter(Voter::hasVoted)
                                .map(Voter::getName)
                                .sorted()
                                .toList();
                System.out.println("Voted: " + votedVoters);
                calculateStatistics();
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    private Map<String, Map<String, Integer>> calculateStatistics () {
        Map<String, Map<String, Integer>> allResults = new HashMap<>();
        this.questionList.forEach(question -> {
            System.out.println("Question is " + question.getText() + "\n");
            System.out.println("And the result are as follows: ");
            Map<String, Integer> results = new HashMap<>();
            question.getOptions().forEach(option -> {
                results.put(option, 0);
            });
            this.voters.stream().filter(Voter::hasVoted).forEach(voter -> {
                String answer = voter.getAnswerForQuestion(question.getText());
                Integer currentValue = results.get(answer);
                results.put(answer, currentValue + 1);
            });
            allResults.put(question.getText(), results);
            System.out.println(results);

        });
        return allResults;
    }

    private void simulate () {
        Random random = new Random();

        new Thread(() -> {
            this.voters.forEach(voter -> {
                this.questionList.forEach(question -> {
                    int totalOptions = question.getOptions().size();
                    String randomAnswer = question.getOptions().get(random.nextInt(totalOptions));
                    voter.updateAnswer(question.getText(), randomAnswer);
                });
                try {
                    Thread.sleep(random.nextInt(3, 5));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            export ();

        }).start();
    }

    private void export () {
        try {
            File file = new File("C:\\Users\\podium\\Downloads\\1\\results.txt");
            FileWriter fileWriter = new FileWriter(file);
            Map<String, Map<String, Integer>> allResults = calculateStatistics();
            for (String question : allResults.keySet()) {
                fileWriter.append(question).append("\n");
                Map<String, Integer> results = allResults.get(question);
                for (String option : results.keySet()) {
                    Integer amount = results.get(option);
                    fileWriter.append(option).append(": ").append(String.valueOf(amount)).append("\n");
                }
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
