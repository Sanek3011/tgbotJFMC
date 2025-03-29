package org.example.controller;

import org.example.handler.*;
import org.example.model.Role;
import org.example.model.State;
import org.example.model.User;
import org.example.service.UserService;
import org.example.util.KeyboardUtil;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TGBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "7918103842:AAH6fYagCHDh9j_8XHiScMVQHQSb9uwJq50";
    private static final String BOT_NAME = "JFMC05";
    private final UserService service = new UserService();
    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

    public TGBot() {
        registerCommands();
        commandHandlers.put("getAllUsers", new GetAllUsersCommandHandler(this));
        commandHandlers.put("registration", new RegistrationCommandHandler(this));
        commandHandlers.put("getMyReports", new GetMyReportsCommandHandler(this));
        commandHandlers.put("createReport", new CreateReportCommandHandler(this));
        commandHandlers.put("info", new InfoCommandHandler(this));
        commandHandlers.put("getAllReports", new GetAllReportsCommandHandler(this));
        commandHandlers.put("getReportsByUsernameAndDate", new GetReportByUsernameAndDate(this));
        commandHandlers.put("deleteUser", new DeleteUserCommandHandler(this));
        commandHandlers.put("changeRole", new ChangeRoleCommandHandler(this));
    }

    private void registerCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/keyboard", "Показать действия"));
        commands.add(new BotCommand("/nickname", "Установить имя"));


        try {
            this.execute(new SetMyCommands(commands, null, null));
        }catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToUser(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void textHandler(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();
        User user = service.getUserByTgId(chatId);

        if (user == null || "/start".equals(text)) {
            CommandHandler commandHandler = commandHandlers.get("registration");
            if (commandHandler != null) {
                commandHandler.handle(update, user);
            }
        }
        if ("quit".equals(text)) {
            service.updateUserState(user, State.NO);
        }
        handleState(update, user);

        if ("/keyboard".equals(text)) {
            InlineKeyboardMarkup keyboardMarkup = KeyboardUtil.getKeyboardMarkup(user);
            sendKeyboard(chatId, keyboardMarkup);

        }

        if (text.startsWith("/nickname")) {
            String[] mass = text.split(" ");
            service.updateUsername(chatId, mass[1]);
            sendMessageToUser(chatId, "Никнейм успешно установлен");
        }
    }

    public void handleState(Update update, User user) {
        switch (user.getState()) {
            case WAITING_NICKDATA:
                commandHandlers.get("getReportsByUsernameAndDate").handle(update,user);
                break;
            case TYPE_DONE:
            case DESCRIPTION_DONE:
            case IMG_DONE:
                commandHandlers.get("createReport").handle(update, user);
                break;
            case WAITING_DELETE:
                commandHandlers.get("deleteUser").handle(update, user);
                break;
            case WAITING_CHANGEROLE:
                commandHandlers.get("changeRole").handle(update, user);
            default:
                break;


        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        User user;

        if (update.hasMessage() && update.getMessage().hasText()) {
            textHandler(update);

        }
        if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            user = service.getUserByTgId(chatId);
            callbackQuery(update, user);
        }

    }

    private void callbackQuery(Update update, User user) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();
        System.out.println(data);
        if (data.startsWith("REPORT_TYPE")) {
            CommandHandler commandHandler = commandHandlers.get("createReport");
            commandHandler.handle(update, user);
        }
        CommandHandler commandHandler = commandHandlers.get(data);
        if (commandHandler != null) {
            commandHandler.handle(update, user);
        }

    }

    public void sendKeyboard(Long chatId, InlineKeyboardMarkup inlineKeyboardMarkup) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Нажмите на одну из кнопок");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
