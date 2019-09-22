package me.raspberry.freaking.quicktranslatebot.services;

import okhttp3.*;

import java.io.IOException;

public class TranslationService {
    private String yandexSubscriptionKey;
    private static final String SCHEME = "https";
    private static final String HOST = "translate.yandex.net";
    private static final String SEGMENTS = "api/v1.5/tr.json/translate";

    private OkHttpClient client = new OkHttpClient();

    public TranslationService(final String yandexSubscriptionKey) {
        this.yandexSubscriptionKey = yandexSubscriptionKey;
    }

    public String post(String language, String input) throws IOException {
        String languageParameter = language + "-" + (language.equals("en") ? "ru" : "en");
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme(SCHEME)
                        .host(HOST)
                        .addPathSegments(SEGMENTS)
                        .addQueryParameter("key", yandexSubscriptionKey)
                        .addQueryParameter("text", input)
                        .addQueryParameter("lang", languageParameter).build())
                .post(body)
                .build();
        return ResponseParserService.getResponse(request, "text", client);
    }
}
