package org.example.handler;

import org.example.controller.TGBot;
import org.example.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DeleteUserCommandHandler implements CommandHandler {
    private TGBot bot;
    private UserService service = new UserService();

    public DeleteUserCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {

    }
}
