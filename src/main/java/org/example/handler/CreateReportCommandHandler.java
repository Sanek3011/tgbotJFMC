package org.example.handler;

import org.example.controller.TGBot;
import org.example.model.*;
import org.example.service.ReportService;
import org.example.service.UserService;
import org.example.util.KeyboardUtil;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.*;

public class CreateReportCommandHandler implements CommandHandler {
    private final ReportService reportService = new ReportService();
    private final UserService userService = new UserService();
    private final TGBot bot;
    private static final Map<Long, ReportSession> tmp = new HashMap<>();

    public CreateReportCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        Long chatId = null;
        CallbackQuery callbackQuery = update.getCallbackQuery();// сюда не приходит айдишник в случае ТЕКСТА

        if (callbackQuery == null) {
            chatId = update.getMessage().getChatId();
        }else{
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }

        System.out.println(chatId);

        tmp.putIfAbsent(chatId, new ReportSession());

        ReportSession reportSession = tmp.get(chatId);
        System.out.println("ReportSession before: " + reportSession);


        switch (user.getState()) {
            case NO:
                getTypeOfReport(chatId, user);
                break;
            case TYPE_DONE:
                getDescOfReport(update, reportSession, chatId, user);
                break;
            case DESCRIPTION_DONE:
                getImgOfReport(update, reportSession, user, chatId);
                break;
            case IMG_DONE:
                createAndSave(update, reportSession, user, chatId);
                break;
        }
    }

    private void createAndSave(Update update, ReportSession reportSession, User userByTgId, Long chatId) {
        String text = update.getMessage().getText();
        reportSession.setImgUrl(text);
        userService.updateUserState(userByTgId, State.NO);
        Report report = ReportSession.toReport(reportSession);
        report.setDateOfCreation(LocalDate.now());
        report.setUser(userByTgId);
        reportService.saveReport(report);
        bot.sendMessageToUser(chatId, "Ваш отчет успешно отправлен");
    }

    private void getImgOfReport(Update update, ReportSession reportSession, User userByTgId, Long chatId) {
        String text = update.getMessage().getText();
        reportSession.setDescription(text);
        userService.updateUserState(userByTgId, State.IMG_DONE);
        bot.sendMessageToUser(chatId, "Загрузите скрины (строго ссылка на imgur)");
    }

    private void getDescOfReport(Update update, ReportSession reportSession, Long chatId, User user) {
        String data = update.getCallbackQuery().getData();
        String[] tmp = data.split(":");
        reportSession.setType(ReportType.valueOf(tmp[1].toUpperCase()));
        System.out.println("ReportSession after: " + CreateReportCommandHandler.tmp.get(chatId));

        bot.sendMessageToUser(chatId, "Введите описание проделанной работы");
        userService.updateUserState(user, State.DESCRIPTION_DONE);

    }

    private void getTypeOfReport(Long chatId, User user) {
        bot.sendMessageToUser(chatId, "Выберите тип отчета");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (ReportType type : ReportType.values()){
            InlineKeyboardButton button = KeyboardUtil.createButton(type.name(), "REPORT_TYPE:" + type.name());
            rowsInline.add(Collections.singletonList(button));
        }
        markup.setKeyboard(rowsInline);
        System.out.println("ReportSession after: " + tmp.get(chatId));
        bot.sendKeyboard(chatId, markup);
        userService.updateUserState(user, State.TYPE_DONE);
    }
}
