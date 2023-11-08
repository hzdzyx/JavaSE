package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost",8888);
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(),true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        printWriter.println("Hello");
        clientSocket.close();
    }

}
