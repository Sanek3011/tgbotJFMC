package org.example.handler;

import org.example.model.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    void handle(Update update, User user);
}
