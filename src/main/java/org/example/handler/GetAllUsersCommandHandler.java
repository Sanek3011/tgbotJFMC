package org.example.handler;
import org.example.controller.TGBot;
import org.example.model.Role;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.example.model.User;
import org.example.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class GetAllUsersCommandHandler implements CommandHandler {
    private final UserService service = new UserService();
    private final TGBot bot;

    public GetAllUsersCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        if (!user.getRole().equals(Role.ADMIN)) {
            return;
        }
        List<User> allUsers = service.getAllUsers();

        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        StringBuilder messageText = new StringBuilder();

        for (User usertmp : allUsers) {
            messageText.append(usertmp.getName()+" "+usertmp.getRole()).append("\n");
        }

        bot.sendMessageToUser(chatId, messageText.toString());
    }
}
