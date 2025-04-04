package org.example.handler;

import org.example.controller.TGBot;
import org.example.model.Report;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.ReportService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class GetAllReportsCommandHandler implements CommandHandler {
    private final TGBot bot;
    private final ReportService service = new ReportService();

    public GetAllReportsCommandHandler(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        if (!user.getRole().equals(Role.ADMIN)) {
            return;
        }
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        List<Report> allReports = service.getAllReports();
        if (allReports.isEmpty()) {
            bot.sendMessageToUser(chatId, "Отчетов не найдено");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Report report : allReports) {
            sb.append(report);
            sb.append("\n");
        }
        bot.sendMessageToUser(chatId, sb.toString());
    }
}
