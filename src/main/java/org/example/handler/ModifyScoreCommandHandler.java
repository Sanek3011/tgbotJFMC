package org.example.handler;

import org.example.controller.TGBot;
import org.example.model.Role;
import org.example.model.State;
import org.example.model.User;
import org.example.service.UserService;
import org.example.util.KeyboardUtil;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;

public class ModifyScoreCommandHandler implements CommandHandler {
    private final UserService service = new UserService();
    private static final Map<Long, User> selectedUser = new HashMap<>();
     private final TGBot bot;

    public ModifyScoreCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        if (!user.getRole().equals(Role.ADMIN)){
            return;
        }
        Long chatId;

        if (update.getCallbackQuery() != null) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }else {
            chatId = update.getMessage().getChatId();
        }
        switch (user.getState()) {
            case NO:
                bot.sendMessageToUser(chatId, "Введите имя того, кому хотите начиcлить/снять баллы");
                service.updateUserState(user, State.WAITING_NICKSCORE);
                break;
            case WAITING_NICKSCORE:
                String text = update.getMessage().getText();
                User userByName = service.getUserByName(text);
                if (userByName == null) {
                    bot.sendMessageToUser(chatId, "некорректный ник, попробуйте снова");
                    break;
                }
                service.updateUserState(user, State.WAITING_SCOREKEYBOARD);
                InlineKeyboardMarkup scoreKeyboard = KeyboardUtil.getScoreKeyboard();
                bot.sendKeyboard(chatId, scoreKeyboard);
                break;
            case WAITING_SCOREKEYBOARD:
                String data = update.getCallbackQuery().getData();
                System.out.println(">>>>>>>>>>>>>"+data);
                String[] parts = data.split("(?<=\\D)(?=\\d)");
                service.updateUserScore(user, parts[0], parts[1]);
                service.updateUserState(user, State.NO);
                bot.sendMessageToUser(chatId, "Баллы были начислены/списаны");

        }




    }
}
