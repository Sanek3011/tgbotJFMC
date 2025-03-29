package org.example.handler;

import org.example.controller.TGBot;
import org.example.dao.UserDao;
import org.example.model.Role;
import org.example.model.State;
import org.example.model.User;
import org.example.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RegistrationCommandHandler implements CommandHandler{

    private final UserService service = new UserService();
    private final TGBot bot;

    public RegistrationCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {

        Long chatId = update.getMessage().getChatId();

        System.out.println(chatId);


        if (user == null) {
            User newUser = User.builder()
                    .telegramId(chatId)
                    .role(Role.GUEST)
                    .score(0)
                    .state(State.NO)
                    .build();
            service.saveUser(newUser);
            bot.sendMessageToUser(chatId, "Добро пожаловать. Установите ваш ник по /nickname Nick_Name");

        }


    }
}
