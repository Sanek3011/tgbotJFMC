package org.example.handler;

import org.example.controller.TGBot;
import org.example.model.Report;
import org.example.model.User;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class InfoCommandHandler implements CommandHandler{

    private final TGBot bot;

    public InfoCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        StringBuilder sb = new StringBuilder();
        sb.append("Добро пожаловать в БОТ JFMC05\n Никнейм в боте должен совпадать с игровым \n Для получения доступа обратитесь в конфу дискорда, в канал руководства, тегнув sanek3011 \n Список доступных комманд - /keyboard, выход из меню введите quit \n По всем вопросам и найденным багам писать в тг @temsasa\n");
        sb.append("----------------");
        sb.append("Информация об аккаунте:\n");
        sb.append("Количество баллов: ").append(user.getScore()).append("\n Количество ваших отчетов: ").append(user.getReports().size());
        String info = sb.toString();
        System.out.println(info);
        bot.sendMessageToUser(chatId, info);

    }
}
