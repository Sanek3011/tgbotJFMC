package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.controller.TGBot;
import org.example.dao.UserDao;
import org.example.model.Role;
import org.example.model.State;
import org.example.model.User;
import org.example.service.UserService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
@Slf4j
public class ChangeRoleCommandHandler implements CommandHandler {
    private UserService service = new UserService();
    private TGBot bot;
    public ChangeRoleCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        if (!user.getRole().equals(Role.ADMIN)) {
            return;
        }
        Long chatId = null;
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null) {
            chatId = callbackQuery.getMessage().getChatId();
            log.info("Запрос смены роли от {}", chatId);
            service.updateUserState(user, State.WAITING_CHANGEROLE);
            bot.sendMessageToUser(chatId, "Введите ник и роль через пробел. Пример: Nick_Name USER. Доступные роли: GUEST, USER");
        }else {
            chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            log.info("Запрос смены роли для {} от {}", text, chatId);
            String[] tmp = text.split(" ");
            if (tmp.length != 2) {
                bot.sendMessageToUser(chatId, "Некорректный ввод. Пример: Nick_Name USER. Доступные роли: GUEST, USER");
                log.warn("Некорректный ввод {}", text);
                return;
            }
            try {
                User userByName = service.getUserByName(tmp[0]);
                String role = tmp[1];
                System.out.println("ROLE in HANDLER---->"+role);
                service.changeRoleByUsername(userByName, role);
                service.updateUserState(user, State.NO);
                bot.sendMessageToUser(chatId, "Роль успешна установлена");
            }catch (IllegalArgumentException e) {
                bot.sendMessageToUser(chatId, "Неверно введена роль. Для выхода введите quit");

        }catch (Exception e) {
                bot.sendMessageToUser(chatId, "Пользователь не найден. Если хотите выйти введите quit");
            }
        }
    }
}
