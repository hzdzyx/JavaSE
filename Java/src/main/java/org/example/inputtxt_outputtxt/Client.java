package org.example.inputtxt_outputtxt;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost",8888);
        OutputStream outputStream = clientSocket.getOutputStream();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("client.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        String message;
        while ((message = bufferedReader.readLine())!=null)bufferedWriter.write(message+'\n');
        bufferedWriter.flush();
        clientSocket.close();
    }
}
