module com.project.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;

    requires spring.websocket;
    requires spring.messaging;
    requires tyrus.standalone.client;
    requires org.apache.httpcomponents.httpmime;
    requires java.desktop;
    requires spring.core;

    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires annotations;

    opens com.project.application.model to com.google.gson;

    opens com.project.application.controllers to javafx.fxml;
    exports com.project.application.controllers;

    opens com.project.application to javafx.fxml;
    exports com.project.application;
}