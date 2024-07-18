package org.example;

public class Reminder {
    private long chatId;
    private String text;
    private int seconds;

    public Reminder(long chatId) {
        this.chatId = chatId;
    }

    public boolean hasText () {
        return this.text != null;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
