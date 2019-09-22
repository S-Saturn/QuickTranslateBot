package me.raspberry.freaking.quicktranslatebot.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

class ResponseParserService {
    static String getResponse(Request request, String key, OkHttpClient client) throws IOException {
        String output;
        Response response = client.newCall(request).execute();
        assert response.body() != null;
        JSONObject jsonResponse = new JSONObject(response.body().string());
        if (key.equals("text")) {
            JSONArray translations = jsonResponse.getJSONArray(key);
            output = translations.getString(0);
            StringBuilder stringBuilder = new StringBuilder(output);
            for (int i = 1; i < translations.length(); i++) {
                stringBuilder.append("; \r\n");
                stringBuilder.append(translations.getString(i));
            }
            return stringBuilder.toString();
        } else {
            return jsonResponse.getString(key);
        }
    }
}
