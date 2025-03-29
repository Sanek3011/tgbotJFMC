package org.example.handler;

import org.example.controller.TGBot;
import org.example.model.Report;
import org.example.model.State;
import org.example.model.User;
import org.example.service.ReportService;
import org.example.service.UserService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.util.List;

public class GetReportByUsernameAndDate implements CommandHandler {
    private final TGBot bot;
    private final ReportService reportService = new ReportService();
    private final UserService userService = new UserService();

    public GetReportByUsernameAndDate(TGBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update, User user) {
        Long chatId = null;
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null) {
            chatId = callbackQuery.getMessage().getChatId();
            userService.updateUserState(user, State.WAITING_NICKDATA);
            bot.sendMessageToUser(chatId, "Введите ник и кол-во дней, за которые хотите отчет ПРИМЕР: Nick_Name 7");
        }else{
            chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            String[] tmp = text.split(" ");
            if (tmp.length != 2) {
                bot.sendMessageToUser(chatId, "Некорректный ввод. Введите по форме - Nick_Name 7, где 7 - количество дней за который вам нужен список отчетов");
                return;
            }
            try {
                User findUser = userService.getUserByName(tmp[0]);
                Long minusDays = Long.parseLong(tmp[1]);
                LocalDate date = LocalDate.now().minusDays(minusDays);
                List<Report> reportsByUsernameAndDate = reportService.getReportsByUsernameAndDate(findUser, date);
                if (reportsByUsernameAndDate.isEmpty()) {
                    bot.sendMessageToUser(chatId, "Отчетов не найдено за выбранный промежуток");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (Report report : reportsByUsernameAndDate) {
                    sb.append(report).append("\n");

                }
                System.out.println(sb);
                userService.updateUserState(user, State.NO);
                bot.sendMessageToUser(chatId, sb.toString());
            }catch (NumberFormatException e) {
                bot.sendMessageToUser(chatId, "Некорректный ввод. Введите по форме - Nick_Name 7, где 7 - количество дней за который вам нужен список отчетов. Для отмены введите quit");
            }catch (Exception e) {
                bot.sendMessageToUser(chatId, "Пользователь не найден. Для отмены введите quit");
            }

        }

    }
}
