package com.project.application.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.project.application.Runner;
import com.project.application.api.Api;
import com.project.application.config.Config;
import com.project.application.draw.ChatDraw;
import com.project.application.entity.ChatEntity;
import com.project.application.events.NewMessage;
import com.project.application.events.SendMessage;
import com.project.application.model.ChatModel;
import com.project.application.model.MessageModel;
import com.project.application.model.ThisMe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class MainController extends Application{

    /*
     FXML Variables for panel button
     */
    @FXML private Pane paneClose;
    @FXML private Pane paneCollapse;
    /*
     FXML Variables for yourself
     */
    @FXML private Circle circlePhoto;
    /*
     FXML Variables for ChatList
     */
    @FXML private AnchorPane anchorPaneChats;
    /*
     FXML Variables for ChatDialogue
     */
    @FXML private Group groupSelectDialog;
    @FXML private Pane paneDialog;
    @FXML private Pane paneNoSelectChat;
    @FXML private ScrollPane scrollPaneDialog;
    @FXML private AnchorPane anchorPaneDialog;
    @FXML private Label labelDialogName;
    @FXML private Label labelDialogUserName;
    @FXML private Circle circlePhotoDialog;
    @FXML private TextField textFieldDialogMessage;
    /*
     Variables for moving scene
     */
    private double offsetPosX;
    private double offsetPosY;

    /*
     Objects
     */
    private Api api = new Api("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU3ODg0Nzk5LCJleHAiOjE2NTg0ODk1OTl9.c0Eibp1HMZwfEao5M6m0sGYjaOvkYsZqtD61n3ZbhdZqk34RIZUtM1JOpuJfNs3NpNe9IqY5tmmh4DfUn4nX2Q\n");
    private Config config = new Config();

    @Override
    public void start(@NotNull Stage stage) throws Exception {;
        Image iconImage = new Image(new File(config.getPath() + "/icons/main.png").toURI().toString());

        FXMLLoader fxmlLoader = new FXMLLoader(Runner.class.getResource("scene/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        stage.setResizable(false);
        stage.setTitle("Alpha Test");
        stage.getIcons().add(iconImage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        // For Used to move the scene
        scene.setOnMousePressed(event -> {
            offsetPosX = stage.getX() - event.getScreenX();
            offsetPosY = stage.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + offsetPosX);
            stage.setY(event.getScreenY() + offsetPosY);
        });
    }

    /**
     * The function starts to draw, it is executed after the scene starts.
     */
    @FXML
    public void initialize() throws Exception {
        new CloseAndCollapse();

        new YourSelf().getInformation();
        new ChatsList().drawChatList();

        new WebClient(YourSelf.uuid);

        //System.out.println(api.getSubscribeStickers());
    }

    /**
     * This class performs actions with the main buttons of the panel.
     */

    public class CloseAndCollapse {

        public CloseAndCollapse(){

            MouseOnEnteredCloseButton();
            MouseOnExitCloseButton();
            MouseClickedCloseButton();

            MouseOnEnteredCollapseButton();
            MouseOnExitCollapseButton();
            MouseClickedCollapseButton();
        }

        public void MouseOnEnteredCloseButton(){
            paneClose.setOnMouseEntered(event ->
                    paneClose.setStyle("-fx-background-color: #0e0e0e"));
        }

        public void MouseOnExitCloseButton(){
            paneClose.setOnMouseExited(event ->
                    paneClose.setStyle("-fx-background-color: #1e1e1e"));
        }

        public void MouseClickedCloseButton(){
            paneClose.setOnMouseClicked(event -> {
                Stage stage = (Stage) paneClose.getScene().getWindow();
                stage.close();
            });
        }

        public void MouseOnEnteredCollapseButton(){
            paneCollapse.setOnMouseEntered(event ->
                    paneCollapse.setStyle("-fx-background-color: #0e0e0e"));
        }

        public void MouseOnExitCollapseButton(){
            paneCollapse.setOnMouseExited(event ->
                    paneCollapse.setStyle("-fx-background-color: #1e1e1e"));
        }

        public void MouseClickedCollapseButton(){
            paneCollapse.setOnMouseClicked(event -> {
                Stage stage = (Stage) paneCollapse.getScene().getWindow();
                stage.setIconified(true);
            });
        }

    }

    public class YourSelf {

        private static Long id;
        private static String uuid;

        /**
         * This function does the painting of your photo, and also gets the necessary objects for the entire application to work.
         */
        @FXML
        public void getInformation() {
            ThisMe thisMe = api.getInfo();

            id = thisMe.getId();
            uuid = thisMe.getUuid();

            circlePhoto.setFill(new ImagePattern(new Image(thisMe.getFileNameAvatar())));
        }
    }

    /**
     * This class is used to interact with the chat, drawing, sorting, deleting.
     */
    public class ChatsList {

        private final int anchorPaneHeight = 695;
        private static List<ChatEntity> chatEntityList = new ArrayList<>();

        private Pane activePane;
        /**
         * This function is used to get and draw the list of chats!
         */
        @FXML
        public void drawChatList() throws IOException {

            List<ChatModel> list = api.getChats();
            // Clear anchorpane
            anchorPaneChats.getChildren().clear();
            // If our list is empty, then we draw an image and text
            if(list == null || list.size() == 0) {
                ImageView imageView = new ImageView(new Image(new File(config.getPath() + "/images/chat/sad.png").toURI().toString()));
                imageView.setLayoutX(151);
                imageView.setLayoutY(210);
                imageView.setFitWidth(48);
                imageView.setFitHeight(48);

                Label label = new Label("Нету чатов:(");
                label.setLayoutX(96);
                label.setLayoutY(267);
                label.setPrefWidth(159);
                label.setPrefHeight(27);
                label.setTextFill(Paint.valueOf("#c6c6c6"));
                label.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
                label.setAlignment(Pos.CENTER);

                anchorPaneChats.getChildren().addAll(imageView, label);
                return;
            }

            Collections.sort(list, new Comparator<ChatModel>() {
                @Override
                public int compare(ChatModel h, ChatModel v) {
                    return new Date(v.getLastMessageDate()).compareTo(new Date(h.getLastMessageDate()));
                }
            });

            for(int count = 0; count < list.size(); count++) {

                ChatModel chatModel = list.get(count);

                Pane pane = new Pane();
                pane.setLayoutX(0);
                pane.setLayoutY(52*count);
                pane.setPrefWidth(350);
                pane.setPrefHeight(52);
                pane.setCursor(Cursor.HAND);
                pane.setStyle("-fx-background-color : #212121");

                Circle circlePhoto = new Circle();
                //circlePhoto.setId("circlePhoto");
                circlePhoto.setLayoutX(31);
                circlePhoto.setLayoutY(26);
                circlePhoto.setRadius(20);
                circlePhoto.setFill(new ImagePattern(new Image(chatModel.getFileNameAvatar())));

                Label labelName = new Label(chatModel.getName());
                labelName.setLayoutX(62);
                labelName.setLayoutY(3);
                labelName.setPrefWidth(238);
                labelName.setPrefHeight(17);
                labelName.setAlignment(Pos.CENTER_LEFT);
                labelName.setFont(Font.font("Consolas", 14));
                labelName.setTextFill(Paint.valueOf("#9e9e9e"));

                Label labelLastMessage = new Label((chatModel.getLastSenderId() == YourSelf.id ? "Вы: " : "") + chatModel.getLastMessage());
                labelLastMessage.setLayoutX(62);
                labelLastMessage.setLayoutY(20);
                labelLastMessage.setPrefWidth(250);
                labelLastMessage.setPrefHeight(20);
                labelLastMessage.setAlignment(Pos.TOP_LEFT);
                labelLastMessage.setFont(Font.font("Consolas", FontWeight.BOLD, 12));
                labelLastMessage.setTextFill(Paint.valueOf("#807e7e"));

                String date = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru", "RU")).format(new Date(chatModel.getLastMessageDate()));
                String clock = new SimpleDateFormat("HH:mm", new Locale("ru", "RU")).format(new Date(chatModel.getLastMessageDate()));

                Label labelLastMessageDate = new Label(date + " в " + clock);
                labelLastMessageDate.setLayoutX(210);
                labelLastMessageDate.setLayoutY(4);
                labelLastMessageDate.setPrefWidth(123);
                labelLastMessageDate.setPrefHeight(15);
                labelLastMessageDate.setAlignment(Pos.CENTER_RIGHT);
                labelLastMessageDate.setFont(Font.font("Consolas", 11));
                labelLastMessageDate.setTextFill(Paint.valueOf("#9a9999"));

                Circle circleCounter = new Circle();
                circleCounter.setLayoutX(320);
                circleCounter.setLayoutY(34);
                circleCounter.setRadius(10);
                circleCounter.setFill(Paint.valueOf("#1e90ff"));
                circleCounter.setVisible(chatModel.getCountNewMessage() != 0);

                Label labelCounter = new Label(chatModel.getCountNewMessage() > 9 ? "9+" : String.valueOf(chatModel.getCountNewMessage()));
                labelCounter.setLayoutX(chatModel.getCountNewMessage() > 9 ? 314 : 313);
                labelCounter.setLayoutY(25);
                labelCounter.setPrefWidth(15);
                labelCounter.setPrefHeight(15);
                labelCounter.setAlignment(Pos.CENTER);
                labelCounter.setFont(Font.font("Consolas", FontWeight.BOLD, 15));
                labelCounter.setTextFill(Paint.valueOf("white"));
                labelCounter.setVisible(chatModel.getCountNewMessage() != 0);

                pane.getChildren().addAll(circlePhoto, labelName, labelLastMessage, labelLastMessageDate, circleCounter, labelCounter);
                ChatDraw chatDraw = new ChatDraw(pane, labelName, labelLastMessage, labelLastMessageDate, circleCounter, labelCounter);
                ChatEntity chatEntity = new ChatEntity(chatModel, chatDraw);

                chatEntityList.add(chatEntity);
                anchorPaneChats.getChildren().add(pane);

                pane.setOnMouseEntered(event ->
                    pane.setStyle("-fx-background-color : " + ( activePane != pane ? "#181818" : "#111111" ) ));
                pane.setOnMouseExited(event ->
                        pane.setStyle("-fx-background-color : " + ( activePane != pane ? "#212121" : "#141414" ) ));
                pane.setOnMouseClicked(event -> {
                    if(activePane == pane) {
                        return;
                    }
                    if(activePane != null) {
                        activePane.setStyle("-fx-background-color: #212121");
                    }
                    activePane = pane;
                    pane.setStyle("-fx-background-color: #111111");
                    new ChatDialogue().drawChatDialogue(chatModel);
                });
            }
        }

        /**
         * This function draws the last message in the chat list.
         * @param chatEntity
         */
        @FXML
        public void drawLastMessage(ChatEntity chatEntity, String text, Long senderId, Long date, Long counter){

            chatEntityList.remove(chatEntity);

            String day = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru", "RU")).format(new Date(date));
            String clock = new SimpleDateFormat("HH:mm", new Locale("ru", "RU")).format(new Date(date));

            chatEntity.getChatDraw().getLabelLastMessage().setText((senderId == YourSelf.id ? "Вы: " : "") + text);
            chatEntity.getChatDraw().getLabelLastMessageDate().setText(day + " в " + clock);

            chatEntity.getChatDraw().getCircleCounter().setVisible(counter != 0);
            chatEntity.getChatDraw().getLabelCounter().setVisible(counter != 0);
            chatEntity.getChatDraw().getLabelCounter().setLayoutX(counter > 9 ? 314 : 313);
            chatEntity.getChatDraw().getLabelCounter().setText(counter > 9 ? "9+" : String.valueOf(counter));

            chatEntity.getChatModel().setLastMessage(text);
            chatEntity.getChatModel().setLastMessageDate(date);
            chatEntity.getChatModel().setCountNewMessage(counter);
            chatEntity.getChatModel().setLastSenderId(senderId);

            chatEntityList.add(chatEntity);
        }

        /**
         * This function sorts the list with sites by date, and then redraws the new.
         */

        @FXML
        public void sortedList(){
            Collections.sort(chatEntityList, new Comparator<ChatEntity>() {
                @Override
                public int compare(ChatEntity h, ChatEntity v) {
                    return new Date(v.getChatModel().getLastMessageDate()).compareTo(new Date(h.getChatModel().getLastMessageDate()));
                }
            });

            for(int i = 0; i < chatEntityList.size(); i++){
                ChatEntity chatEntity = chatEntityList.get(i);
                chatEntity.getChatDraw().getPane().setLayoutY(52*i);
            }
        }

        /**
         * This function searches for and returns an object by chat number.
         * @param userId
         * @return ChatEntity
         */

        public ChatEntity getChat(Long userId){

            for(int i = 0; i < chatEntityList.size(); i++) {
                ChatEntity chatEntity = chatEntityList.get(i);
                if(chatEntity.getChatModel().getUserId() != userId )
                    continue;

                return chatEntity;
            }
            return null;
        }
    }

    /**
     * This class is used to draw the opening of a dialog with the user.
     */
    public class ChatDialogue {

        public static Long userId;

        /**
         * The function is used to get and draw a dialog with messages.
         * @param chatModel
         */
        @FXML
        public void drawChatDialogue(@NotNull ChatModel chatModel) {

            userId = chatModel.getUserId();

            paneNoSelectChat.setVisible(false);
            groupSelectDialog.setVisible(true);

            labelDialogName.setText(chatModel.getName());
            labelDialogUserName.setText("@" + chatModel.getUsername());

            circlePhotoDialog.setFill(new ImagePattern(new Image(chatModel.getFileNameAvatar())));

            new ChatMessages().drawMessages(chatModel.getId());
            ChatEntity chatEntity = new ChatsList().getChat(chatModel.getUserId());
            chatEntity.getChatDraw().getCircleCounter().setVisible(false);
            chatEntity.getChatDraw().getLabelCounter().setVisible(false);

            //sendMessage(chatModel.getId());
            eventClickKey(chatModel.getId());
        }

        /**
         * This function sends a message to a specific chat.
         * @param chatId
         */

        public void sendMessage(Long chatId){
            if(textFieldDialogMessage.getText().trim().isEmpty()){
                return;
            }

            api.sendMessage(chatId, textFieldDialogMessage.getText().trim());
            textFieldDialogMessage.clear();
        }

        @FXML
        public void eventClickKey(Long chatId){
            textFieldDialogMessage.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER && !textFieldDialogMessage.getText().trim().isEmpty()){
                    sendMessage(chatId);
                }
            });
        }
    }

    /**
     * This class is used to draw messages.
     */
    public class ChatMessages {

        private static int sumHeight = 10;
        private static Long userId;
        private static Date date;

        /**
         * This function draws the new message.
         * @param newMessage
         */
        @FXML
        public void drawNewMessage(NewMessage newMessage){

            ChatsList chatsList = new ChatsList();
            ChatEntity chatEntity = chatsList.getChat(newMessage.getUserId());
            if(chatEntity == null){
                return;
            }

            if(ChatDialogue.userId == newMessage.getUserId()){
                api.resetCounter(newMessage.getChatId());
                drawTextMessage(newMessage.getText(), true, new Date(newMessage.getSendDate()));
                newMessage.setNewCountMessages(0L);
            }

            chatsList.drawLastMessage(
                    chatEntity,
                    newMessage.getText(), newMessage.getUserId(),
                    newMessage.getSendDate(),
                    newMessage.getNewCountMessages()
            );
            chatsList.sortedList();
        }

        /**
         * This function draws the sent message.
         * @param sendMessage
         */
        @FXML
        public void drawSendMessage(SendMessage sendMessage){

            ChatsList chatsList = new ChatsList();
            ChatEntity chatEntity = chatsList.getChat(sendMessage.getUserId());
            if(chatEntity == null){
                return;
            }

            if(ChatDialogue.userId == sendMessage.getUserId()){
                drawTextMessage(sendMessage.getText(), false, new Date(sendMessage.getSendDate()));
            }

            chatsList.drawLastMessage(
                    chatEntity,
                    sendMessage.getText(),
                    YourSelf.id,
                    sendMessage.getSendDate(), 0L
            );
            chatsList.sortedList();
        }

        /**
         * The function is used to draw dialog messages.
         * @param id
         */
        @FXML
        public void drawMessages(@NotNull Long id) {

            List<MessageModel> messageModels = api.getMessages(id);
            if(messageModels == null || messageModels.size() == 0) {
                return;
            }

            for(int count = 0; count < messageModels.size(); count++) {
                MessageModel messageModel = messageModels.get(count);

                drawTextMessage(messageModel.getText(),
                        messageModel.getSenderId() != YourSelf.id,
                        new Date(messageModel.getDate())
                );
            }
            anchorPaneDialog.heightProperty().addListener(observable -> scrollPaneDialog.setVvalue(1D));
        }

        /**
         * The function is used to draw the date of the message. If the date was not drawn before.
         * @param messageDate
         */
        @FXML
        public void drawDateMessage(@NotNull Date messageDate){

            if(date != null && ( date.getTime() - messageDate.getTime() ) / 1000 / 60 / 60 / 24 == 0) {
                return;
            }

            Label labelDate = new Label(new SimpleDateFormat("dd MMMM yyyy", new Locale("ru", "RU")).format(messageDate) + " г.");

            Text textDate = new Text(labelDate.getText());
            textDate.setFont(Font.font("Consolas", FontWeight.BOLD, 16));

            labelDate.setLayoutX((881 - textDate.getLayoutBounds().getWidth()) / 2);
            labelDate.setLayoutY(10 + sumHeight);
            labelDate.setPrefWidth(textDate.getLayoutBounds().getWidth() + 20);
            labelDate.setPrefHeight(25);
            labelDate.setTextAlignment(TextAlignment.valueOf("CENTER"));
            labelDate.setStyle("-fx-background-color: #141414;-fx-background-radius: 5px");
            labelDate.setAlignment(Pos.CENTER);
            labelDate.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
            labelDate.setTextFill(Paint.valueOf("#c6c4c6"));

            date = messageDate;
            sumHeight += (int) labelDate.getPrefHeight() + 10;
            anchorPaneDialog.getChildren().add(labelDate);
        }

        /**
         * The function is used to draw a text message.
         * @param input
         * @param drawLeft
         * @param date
         */
        @FXML
        public void drawTextMessage(@NotNull String input, Boolean drawLeft, @NotNull Date date){


            // Calling a function to draw the date of the message
            drawDateMessage(date);

            String text = new String("");
            for(int start = 0; start < input.length(); start += 80) {
                text += input.substring(start, Math.min(input.length(), start + 80)) + System.lineSeparator();
            }

            Text textMessage = new Text(text);
            textMessage.setFont(Font.font("Times New Roman", 15));
            textMessage.setWrappingWidth(
                    textMessage.getLayoutBounds().getWidth() > 500 ? 500 : textMessage.getLayoutBounds().getWidth());

            Pane paneMessage = new Pane();
            paneMessage.setLayoutX(0);
            paneMessage.setLayoutY(sumHeight + 10);
            paneMessage.setPrefWidth(880);
            paneMessage.setPrefHeight(textMessage.getLayoutBounds().getHeight());

            Pane pane = new Pane();
            pane.setPrefWidth(textMessage.getLayoutBounds().getWidth() + 50);
            pane.setPrefHeight(textMessage.getLayoutBounds().getHeight());
            pane.setLayoutX(drawLeft ? 60 : 880 - pane.getPrefWidth() - 60);
            pane.setLayoutY(0);
            pane.setStyle("-fx-background-color: " + (drawLeft ? "#d7d6d7" : "pink") + ";-fx-background-radius: 15px");

            Label labelMessage = new Label(text);
            labelMessage.setPrefWidth(textMessage.getLayoutBounds().getWidth());
            labelMessage.setPrefHeight(textMessage.getLayoutBounds().getHeight());
            labelMessage.setWrapText(true);
            labelMessage.setLayoutX(9);
            labelMessage.setLayoutY(5);
            labelMessage.setFont(Font.font("Times New Roman", 15));
            labelMessage.setAlignment(Pos.TOP_LEFT);
            labelMessage.setTextFill(Paint.valueOf("#222222"));

            Label labelClock = new Label(new SimpleDateFormat("HH:mm").format(date));
            Text textClock = new Text(labelClock.getText());
            textClock.setFont(Font.font("Consolas", 10));
            labelClock.setLayoutX(labelMessage.getPrefWidth() + 10);
            labelClock.setLayoutY(labelMessage.getPrefHeight() - 15);
            labelClock.setFont(Font.font("Consolas", 10));
            labelClock.setTextFill(Paint.valueOf("#111111"));
            labelClock.setPrefWidth(textClock.getLayoutBounds().getWidth());
            labelClock.setPrefHeight(textClock.getLayoutBounds().getHeight());

            pane.getChildren().addAll(labelMessage, labelClock);
            paneMessage.getChildren().add(pane);
            anchorPaneDialog.getChildren().add(paneMessage);

            if((sumHeight += (int) paneMessage.getPrefHeight() + 5) > 652) {
                anchorPaneDialog.setPrefHeight(sumHeight + 20);
            }
            scrollPaneDialog.setVvalue(sumHeight);
        }
    }

    /**
     * This class is responsible for receiving data and performing actions from the socket.
     */
    public class WebClient {

        StompSession session = null;

        public WebClient(String uuid){
            try {
                ListenableFuture<StompSession> socket = this.connect();
                StompSession stompSession = socket.get();

                this.subscribeGreetings(stompSession, uuid);
            } catch (ExecutionException | InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        public ListenableFuture<StompSession> connect() {

            Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
            List<Transport> transports = Collections.singletonList(webSocketTransport);

            SockJsClient sockJsClient = new SockJsClient(transports);
            sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

            WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

            return stompClient.connect("ws://{host}:{port}/ws", new WebSocketHttpHeaders(), new MyHandler(), "localhost", 8745);
        }

        private class MyHandler extends StompSessionHandlerAdapter {
            public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
                System.out.println("connected");
                session = stompSession;
            }
        }

        public void subscribeGreetings(StompSession stompSession, String uuid) throws ExecutionException, InterruptedException {
            stompSession.subscribe("/user/" + uuid + "/events", new StompFrameHandler() {

                public Type getPayloadType(StompHeaders stompHeaders) {
                    return byte[].class;
                }

                public void handleFrame(StompHeaders stompHeaders, Object o) {
                    JsonElement jsonElement = new JsonParser().parse(new String((byte[]) o));
                    switch (jsonElement.getAsJsonObject().get("event").getAsInt()) {
                        case 0:
                            System.out.println("0");
                            return;
                        case 1:
                            Platform.runLater(() -> new ChatMessages().drawNewMessage(new NewMessage(
                                    jsonElement.getAsJsonObject().get("chatId").getAsLong(),
                                    jsonElement.getAsJsonObject().get("userId").getAsLong(),
                                    jsonElement.getAsJsonObject().get("sendDate").getAsLong(),
                                    jsonElement.getAsJsonObject().get("newCountMessages").getAsLong(),
                                    jsonElement.getAsJsonObject().get("text").getAsString())
                            ));
                            return;
                        case 2:
                            Platform.runLater(() -> new ChatMessages().drawSendMessage(new SendMessage(
                                    jsonElement.getAsJsonObject().get("chatId").getAsLong(),
                                    jsonElement.getAsJsonObject().get("userId").getAsLong(),
                                    jsonElement.getAsJsonObject().get("sendDate").getAsLong(),
                                    jsonElement.getAsJsonObject().get("text").getAsString())
                            ));
                            return;
                    }
                }
            });
        }
    }
}
