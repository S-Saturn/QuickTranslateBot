package me.raspberry.freaking.quicktranslatebot;

import me.raspberry.freaking.quicktranslatebot.commands.HelpCommand;
import me.raspberry.freaking.quicktranslatebot.services.LanguageDetectionService;
import me.raspberry.freaking.quicktranslatebot.services.TranslationService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;


public class TranslateBot extends TelegramLongPollingCommandBot {
    private String botToken;
    private String yandexSubscriptionKey;

    TranslateBot(final String botUsername, final String botToken, final String yandexSubscriptionKey) {
        super(botUsername);
        this.botToken = botToken;
        this.yandexSubscriptionKey = yandexSubscriptionKey;
        HelpCommand helpCommand = new HelpCommand();
        register(helpCommand);
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                String language = new LanguageDetectionService(yandexSubscriptionKey).post(update.getMessage().getText());

                String translation = new TranslationService(yandexSubscriptionKey).post(language, update.getMessage().getText());
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(translation + "\r\n\nPowered by Yandex.Translate (http://translate.yandex.com/)");
                execute(message);
            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}

