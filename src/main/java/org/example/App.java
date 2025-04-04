package org.example;

import org.example.controller.TGBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.LocalDate;
import java.util.List;

import static org.example.util.Factory.FACTORY;


public class App 
{
    public static void main( String[] args ){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TGBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
