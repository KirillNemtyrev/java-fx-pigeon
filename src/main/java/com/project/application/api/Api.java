package com.project.application.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import com.project.application.entity.RequestEntity;
import com.project.application.model.ChatModel;
import com.project.application.model.MessageModel;
import com.project.application.model.StickerList;
import com.project.application.model.ThisMe;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
     * This function sends a message to the public chat.
     * @param id
     * @param text
     */

    public RequestEntity sendMessage(@NotNull Long id, @NotNull String text) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            String params = "{ \"chatId\" : " + id + ",\"text\" : \"" + text + "\"}";
            HttpPost request = new HttpPost(host + "api/message");
            request.setHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.addHeader("Authorization", "Bearer " + token);
            request.setEntity(new StringEntity(params, "UTF-8"));

            // We execute the request, create the necessary variables and process the received response
            CloseableHttpResponse response = client.execute(request);
            // We get an answer
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //
            StringBuffer stringBuffer = new StringBuffer();
            String lineForBuffer = "";
            while ((lineForBuffer = bufferedReader.readLine()) != null) {
                stringBuffer.append(lineForBuffer);
            }

            return new RequestEntity(response.getStatusLine().getStatusCode(), stringBuffer.toString());
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

    /**
     * Reset counter messages.
     */
    public void resetCounter(Long chatId){
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(host + "/api/chatroom/counter/id" + chatId);
            httpDelete.setHeader("content-type", "application/json");
            httpDelete.addHeader("Authorization", "Bearer " + token);
            CloseableHttpResponse response = client.execute(httpDelete);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     Functions for stickers
     */
    public List<StickerList> getSubscribeStickers() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(host + "api/sticker/subscribes");
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
            return new Gson().fromJson(stringBuffer.toString(), new TypeToken<List<StickerList>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
