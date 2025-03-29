package org.example.handler;

import org.example.controller.TGBot;
import org.example.model.State;
import org.example.model.User;
import org.example.service.UserService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DeleteUserCommandHandler implements CommandHandler {
    private TGBot bot;
    private UserService service = new UserService();

    public DeleteUserCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        Long chatId = null;
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null) {
            chatId = callbackQuery.getMessage().getChatId();
            service.updateUserState(user, State.WAITING_DELETE);
            bot.sendMessageToUser(chatId, "Введите ник пользователя, которого хотите удалить");
        }else{
            chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            Integer i = service.deleteUser(text);
            service.updateUserState(user, State.NO);
            bot.sendMessageToUser(chatId, i+" пользователей успешно удалено");
        }

    }
}
