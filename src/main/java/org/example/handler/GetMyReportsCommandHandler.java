package org.example.handler;

import org.example.controller.TGBot;
import org.example.dto.ReportDto;
import org.example.model.Report;
import org.example.model.User;
import org.example.service.ReportService;
import org.example.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class GetMyReportsCommandHandler implements CommandHandler{

    private final ReportService service = new ReportService();
    private final UserService userService = new UserService();
    private final TGBot bot;

    public GetMyReportsCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        List<ReportDto> allReportByUser = ReportDto.toDtoList(service.getAllReportByUser(user));
        if (allReportByUser.isEmpty()) {
            bot.sendMessageToUser(chatId, "Отчеты не найдены");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (ReportDto report : allReportByUser) {
            sb.append(report);
            sb.append("---------------------\n");
        }
        bot.sendMessageToUser(chatId, sb.toString());


    }
}
