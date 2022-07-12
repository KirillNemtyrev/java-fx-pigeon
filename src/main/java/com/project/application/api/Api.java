package com.project.application.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.project.application.model.ChatModel;
import com.project.application.model.MessageModel;
import com.project.application.model.ThisMe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.NotNull;

public class Api {

    public static final String host = "http://127.0.0.1:8745/";

    private final int codeOk = 200;
    private final String api = "V1";


    private String token;

    public Api() {}

    public Api(String token) {
        this.token = token;
    }

    public void setToken(String token){
        this.token = token;
    }


    /**
     * This function makes a request for information about yourself.
     */
    public ThisMe getInfo() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(host + "api/settings");
            request.setHeader("content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + token);

            // We execute the request, create the necessary variables and process the received response
            CloseableHttpResponse response = client.execute(request);
            // If response code is bad
            if(response.getStatusLine().getStatusCode() != codeOk) {
                return null;
            }
            // We get an answer
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //
            StringBuffer stringBuffer = new StringBuffer();
            String lineForBuffer = "";
            while ((lineForBuffer = bufferedReader.readLine()) != null) {
                stringBuffer.append(lineForBuffer);
            }
            return new Gson().fromJson(stringBuffer.toString(), ThisMe.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This function returns a list of all user chats. Used only with token!
     **/

    public List<ChatModel> getChats() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(host + "api/chatroom");
            request.setHeader("content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + token);

            // We execute the request, create the necessary variables and process the received response
            CloseableHttpResponse response = client.execute(request);
            // If response code is bad
            if(response.getStatusLine().getStatusCode() != codeOk) {
                return null;
            }
            // We get an answer
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //
            StringBuffer stringBuffer = new StringBuffer();
            String lineForBuffer = "";
            while ((lineForBuffer = bufferedReader.readLine()) != null) {
                stringBuffer.append(lineForBuffer);
            }
            return new Gson().fromJson(stringBuffer.toString(), new TypeToken<List<ChatModel>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This function returns a list of chat messages.
     */
    public List<MessageModel> getMessages(@NotNull Long id) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(host + "api/message/chatId" + id);
            request.setHeader("content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + token);

            // We execute the request, create the necessary variables and process the received response
            CloseableHttpResponse response = client.execute(request);
            // If response code is bad
            if(response.getStatusLine().getStatusCode() != codeOk) {
                return null;
            }
            // We get an answer
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //
            StringBuffer stringBuffer = new StringBuffer();
            String lineForBuffer = "";
            while ((lineForBuffer = bufferedReader.readLine()) != null) {
                stringBuffer.append(lineForBuffer);
            }
            return new Gson().fromJson(stringBuffer.toString(), new TypeToken<List<MessageModel>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
