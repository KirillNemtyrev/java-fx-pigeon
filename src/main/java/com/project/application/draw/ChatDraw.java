package com.project.application.draw;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ChatDraw {

    private Pane pane;
    private Label labelName;
    private Label labelLastMessage;
    private Label labelLastMessageDate;
    private Circle circleCounter;
    private Label labelCounter;
    private Line line;

    public ChatDraw(Pane pane, Label labelName, Label labelLastMessage, Label labelLastMessageDate, Circle circleCounter, Label labelCounter, Line line) {
        this.pane = pane;
        this.labelName = labelName;
        this.labelLastMessage = labelLastMessage;
        this.labelLastMessageDate = labelLastMessageDate;
        this.circleCounter = circleCounter;
        this.labelCounter = labelCounter;
        this.line = line;
    }

    public Pane getPane() {
        return pane;
    }

    public Label getLabelName() {
        return labelName;
    }

    public Label getLabelLastMessage() {
        return labelLastMessage;
    }

    public Label getLabelLastMessageDate() {
        return labelLastMessageDate;
    }

    public Circle getCircleCounter() {
        return circleCounter;
    }

    public Label getLabelCounter() {
        return labelCounter;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
