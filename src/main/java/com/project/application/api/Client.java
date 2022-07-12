package com.project.application.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private static BufferedReader clientReader;

    private static BufferedReader InputStreamRead;
    private static BufferedWriter OutputStreamWrite;

    public void closeConnect() throws IOException {
        clientSocket.close();
        InputStreamRead.close();
        OutputStreamWrite.close();
    }
}
