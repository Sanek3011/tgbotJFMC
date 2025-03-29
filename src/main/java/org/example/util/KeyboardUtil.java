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
            Role.ADMIN, List.of(createButton("Список пользователей", "getAllUsers"),
                    createButton("Все отчеты", "getAllReports"),
                    createButton("Поиск отчета по нику", "getReportsByUsernameAndDate"),
                    createButton("Удалить пользователя", "deleteUser"),
                    createButton("Изменить роль пользователю", "changeRole"))
    );


    public static InlineKeyboardButton createButton(String text, String callback) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callback);
        return button;

    }
    public static InlineKeyboardMarkup getKeyboardMarkup(User user) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (Role role : Role.values()) {
            if (user.getRole().ordinal() >= role.ordinal()) {
                rowsInline.add(ROLE_BUTTONS.getOrDefault(role, List.of()));
            }
        }
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(rowsInline);
        return keyboard;

    }
}
