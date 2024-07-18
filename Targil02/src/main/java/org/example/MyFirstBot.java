package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFirstBot extends TelegramLongPollingBot {
    private Map<Long, Reminder> reminderMap = new HashMap<>();


    @Override
    public void onUpdateReceived(Update update) {
        try {
            long chatId = update.getMessage().getChatId();
            if (reminderMap.containsKey(chatId)) {
                Reminder reminder = this.reminderMap.get(chatId);
                String text = update.getMessage().getText();
                if (!reminder.hasText()) {
                    reminder.setText(text);
                    SendMessage sendMessage = new SendMessage(String.valueOf(chatId), "When to remind? ");
                    execute(sendMessage);
                } else {
                    Integer seconds = Integer.parseInt(text);
                    new Thread(() -> {
                        try {
                            Thread.sleep(seconds * 1000);
                            SendMessage sendMessage = new SendMessage(String.valueOf(chatId),
                                    "Hi " + update.getMessage().getFrom().getFirstName() + ", reminding you to " + reminder.getText());
                            execute(sendMessage);
                            reminder.setText(null);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }
            } else {
                SendMessage sendMessage = new SendMessage(String.valueOf(chatId), "Hi, what is your reminder? ");
                execute(sendMessage);
                Reminder reminder = new Reminder(chatId);
                this.reminderMap.put(chatId, reminder);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "Shai0507";
    }

    @Override
    public String getBotToken () {
        return "7292661637:AAHx3FULA97g6UKGZ6ZzZZw0vby2057X2Fg";
    }

}
