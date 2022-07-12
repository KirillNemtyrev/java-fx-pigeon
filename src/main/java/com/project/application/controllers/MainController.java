package com.project.application.controllers;

import com.project.application.Runner;
import com.project.application.api.Api;
import com.project.application.config.Config;
import com.project.application.model.ChatModel;
import com.project.application.model.MessageModel;
import com.project.application.model.ThisMe;
import eu.hansolo.tilesfx.tools.ConicalGradient;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainController extends Application{

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
    /*
     Variables for moving scene
     */
    private double offsetPosX;
    private double offsetPosY;

    /*
     Objects
     */
    private Api api = new Api("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjU3MzkyOTI3LCJleHAiOjE2NTc5OTc3Mjd9.Y810dpXco485eq1iApbKiHg9qEX9lyv8UUlhOEhI3S3hVdh_CFBp2LJ2vWGgLBONuALEcd8U08k-c-PsgX5sGw");
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
        new YourSelf().getInformation();
        new ChatsList().drawChatList();
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
                    return new Date(h.getLastMessageDate()).compareTo(new Date(v.getLastMessageDate()));
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
                labelName.setFont(Font.font("Consolas", 16));
                labelName.setTextFill(Paint.valueOf("#9e9e9e"));

                Label labelLastMessage = new Label(chatModel.getLastMessage());
                labelLastMessage.setLayoutX(62);
                labelLastMessage.setLayoutY(20);
                labelLastMessage.setPrefWidth(250);
                labelLastMessage.setPrefHeight(20);
                labelLastMessage.setAlignment(Pos.TOP_LEFT);
                labelLastMessage.setFont(Font.font("Consolas", FontWeight.BOLD, 12));
                labelLastMessage.setTextFill(Paint.valueOf("#807e7e"));

                pane.getChildren().addAll(circlePhoto, labelName, labelLastMessage);
                anchorPaneChats.getChildren().add(pane);

                pane.setOnMouseEntered(event -> {
                    pane.setStyle("-fx-background-color : " + ( activePane != pane ? "#181818" : "#111111" ) );
                });
                pane.setOnMouseExited(event -> {
                    pane.setStyle("-fx-background-color : " + ( activePane != pane ? "#212121" : "#141414" ) );
                });
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
            paneNoSelectChat.setVisible(false);
            groupSelectDialog.setVisible(true);

            labelDialogName.setText(chatModel.getName());
            labelDialogUserName.setText("@" + chatModel.getUsername());

            circlePhotoDialog.setFill(new ImagePattern(new Image(chatModel.getFileNameAvatar())));

            new ChatMessages().drawMessages(chatModel.getId());
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
                        messageModel.getSenderId() != id,
                        new Date(messageModel.getDate())
                );
            }
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
}
