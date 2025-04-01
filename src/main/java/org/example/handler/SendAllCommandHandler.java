package org.example.handler;

import org.example.controller.TGBot;
import org.example.model.Role;
import org.example.model.State;
import org.example.model.User;
import org.example.service.UserService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class SendAllCommandHandler implements CommandHandler{
    private final TGBot bot;
    private final UserService service = new UserService();

    public SendAllCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        if (!user.getRole().equals(Role.ADMIN)) {
            return;
        }
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId;
        if (!user.getRole().equals(Role.ADMIN)) {
            return;
        }
        if (callbackQuery != null) {
            chatId = callbackQuery.getMessage().getChatId();
            service.updateUserState(user, State.WAITING_SENDALL);
            bot.sendMessageToUser(chatId, "Введите сообщение для рассылки");
        }else{
            chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            List<Long> allUsersTgId = service.getAllUsersTgId();
            allUsersTgId.remove(chatId);
            for (Long l : allUsersTgId) {
                bot.sendMessageToUser(l, text);
            }
            bot.sendMessageToUser(chatId, "Успешно отправлено");
            service.updateUserState(user, State.NO);
        }

    }
}
