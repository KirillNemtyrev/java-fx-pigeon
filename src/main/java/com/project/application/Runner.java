package com.project.application;

import com.project.application.controllers.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Runner extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainController mainController = new MainController();
        mainController.start(new Stage());
    }

    public static void main(String[] args) {
        launch();
    }
}