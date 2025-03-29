package org.example.handler;

import org.example.controller.TGBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class InfoCommandHandler implements CommandHandler{

    private final TGBot bot;

    public InfoCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String info = "Добро пожаловать в БОТ JFMC05\n Никнейм в боте должен совпадать с игровым \n Для получения доступа обратитесь в конфу дискорда, в канал руководства, тегнув sanek3011 \n Список доступных комманд - /keyboard \n По всем вопросам и найденным багам писать в тг @temsasa";
        bot.sendMessageToUser(chatId, info);

    }
}
