package com.example.sidepot.command.notification.firebase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FirebaseMessageService {

    private final ObjectMapper objectMapper;
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/ozik-c9ba3/messages:send";


    private String getGoogleAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/ozik-c9ba3-firebase-adminsdk-sqly5-bf58bf85ef.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getGoogleAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        System.out.println(response.body().string());
    }

    public void sendMulticastMessage(List<String> tokens, String title, String body) throws IOException {
        String message = makeMessages(tokens, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getGoogleAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        System.out.println(response.body().string());
    }


    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FCMessage fcmFCMessage = FCMessage.builder()
                .message(FCMessage.Message.builder()
                        .token(targetToken)
                        .notification(FCMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmFCMessage);
    }

    private String makeMessages(List<String> targetTokens, String title, String body) throws JsonProcessingException {
        FCMessage fcmFCMessage = FCMessage.builder()
                //.to(targetToken)

//                .notification(FCMessage.Notification.builder()
//                        .title(title)
//                        .body(body)
//                        .build())
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmFCMessage);
    }
}
