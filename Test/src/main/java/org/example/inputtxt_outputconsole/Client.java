package org.example.inputtxt_outputconsole;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket clientsocket = new Socket("localhost",8888);
        OutputStream outputStream = clientsocket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        BufferedReader bufferedReader= new BufferedReader(new FileReader("server.txt"));
        String message;
        while ((message = bufferedReader.readLine())!=null)bufferedWriter.write(message+"\n");
        bufferedWriter.flush();
        clientsocket.close();
    }
}
