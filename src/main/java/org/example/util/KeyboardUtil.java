package org.example.util;

import org.example.model.Role;
import org.example.model.User;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardUtil {

    private static final Map<Role, List<InlineKeyboardButton>> ROLE_BUTTONS = Map.of(
            Role.GUEST, List.of(createButton("Информация", "info")),
            Role.USER, List.of(createButton("Отправить отчет", "createReport"),
                    createButton("Список моих отчетов", "getMyReports")),
            Role.ADMIN, List.of(createButton("Админ панель", "adminPanel"))
    );
    private static final List<List<InlineKeyboardButton>> ADMIN_BUTTONS = List.of(
            List.of(createButton("Список пользователей", "getAllUsers"),
                    createButton("Все отчеты", "getAllReports")),
            List.of(createButton("Поиск отчета по нику", "getReportsByUsernameAndDate"),
                    createButton("Удалить пользователя", "deleteUser")),
            List.of(createButton("Изменить роль пользователю", "changeRole"),
            createButton("Масс.Рассылка", "sendAll")),
            List.of(createButton("Установить баллы", "modifyScore")),
            List.of(createButton("Назад", "backToMain")) // Кнопка для выхода из админ-меню
    );


    public static InlineKeyboardButton createButton(String text, String callback) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callback);
        return button;

    }

    public static InlineKeyboardMarkup getScoreKeyboard() {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        rowsInLine.add(List.of(createButton("+1", "plus1"),
                createButton("-1", "minus1")));
        rowsInLine.add(List.of(createButton("+3", "plus3"),
                createButton("-3", "minus3")));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(rowsInLine);
        return markup;
    }

    public static InlineKeyboardMarkup getKeyboardMarkup(User user, boolean isAdminMenu) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        if (isAdminMenu && user.getRole().equals(Role.ADMIN)) {
            rowsInline.addAll(ADMIN_BUTTONS);
        } else {
            for (Role role : Role.values()) {
                if (user.getRole().ordinal() >= role.ordinal()) {
                    rowsInline.add(ROLE_BUTTONS.getOrDefault(role, List.of()));
                }
            }
        }
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(rowsInline);
        return keyboard;

    }
}
