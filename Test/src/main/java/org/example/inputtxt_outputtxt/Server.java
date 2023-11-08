package org.example.inputtxt_outputtxt;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket clientSocket = serverSocket.accept();
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("server.txt"));
        String message;
        while ((message = bufferedReader.readLine())!=null)bufferedWriter.write(message+'\n');
        bufferedWriter.flush();
        clientSocket.close();
        serverSocket.close();
    }
}
