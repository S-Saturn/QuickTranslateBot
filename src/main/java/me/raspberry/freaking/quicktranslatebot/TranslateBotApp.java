package me.raspberry.freaking.quicktranslatebot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TranslateBotApp {
    private static final String ENV_VARS_NOT_FOUND_MESSAGE = "Cannot run the translate bot: make sure that the " +
            "environment variables TRANSLATE_BOT_NAME, TRANSLATE_BOT_TOKEN, and YANDEX_SUBSCRIPTION_KEY are defined and accessible";
    private static final String TELEGRAM_API_NOT_ACCESSIBLE_MESSAGE = "Cannot access Telegram API: make sure that " +
            "the webhook is free";

    public static void main(String[] args) {
        String botName;
        String botToken;
        String yandexSubscriptionKey;
        try {
            botName = System.getenv("TRANSLATE_BOT_NAME");
            botToken = System.getenv("TRANSLATE_BOT_TOKEN");
            yandexSubscriptionKey = System.getenv("YANDEX_SUBSCRIPTION_KEY");
        } catch (Exception e) {
            System.out.println(ENV_VARS_NOT_FOUND_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (botName == null || botToken == null || yandexSubscriptionKey == null) {
            System.out.println(ENV_VARS_NOT_FOUND_MESSAGE);
            return;
        }

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            TranslateBot translateBot = new TranslateBot(botName, botToken, yandexSubscriptionKey);
            telegramBotsApi.registerBot(translateBot);
        } catch (TelegramApiException e) {
            System.out.println(TELEGRAM_API_NOT_ACCESSIBLE_MESSAGE);
        }
    }
}
