package org.example.inputconsole_outputtxt;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8888);
        OutputStream outputStream = socket.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        printWriter.println(data);
        socket.close();
    }
}
