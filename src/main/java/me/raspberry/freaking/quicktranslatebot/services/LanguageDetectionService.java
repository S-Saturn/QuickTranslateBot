package me.raspberry.freaking.quicktranslatebot.services;


import okhttp3.*;

import java.io.IOException;

public class LanguageDetectionService {
    private String yandexSubscriptionKey;
    private static final String SCHEME = "https";
    private static final String HOST = "translate.yandex.net";
    private static final String SEGMENTS = "api/v1.5/tr.json/detect";
    private static final String LANGUAGES = "en,ru";

    private OkHttpClient client = new OkHttpClient();

    public LanguageDetectionService(final String yandexSubscriptionKey) {
        this.yandexSubscriptionKey = yandexSubscriptionKey;
    }

    public String post(String input) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme(SCHEME)
                        .host(HOST)
                        .addPathSegments(SEGMENTS)
                        .addQueryParameter("key", yandexSubscriptionKey)
                        .addQueryParameter("hint", LANGUAGES)
                        .addQueryParameter("text", input).build())
                .post(body)
                .build();
        return ResponseParserService.getResponse(request, "lang", client);
    }


}
