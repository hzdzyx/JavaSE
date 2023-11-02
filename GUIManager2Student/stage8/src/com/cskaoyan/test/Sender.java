package com.cskaoyan.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * 测试Sender类
 * 按照
 * "stuId=1,name=李明,gender=男,school=xxx,major=软件工程,age=18,city=武汉,phone=13817618878,email=123@qq.com"
 * 的格式
 * 发送一个Student对象
 *
 * @since 09:47
 * @author wuguidong@cskaoyan.onaliyun.com
 */
public class Sender {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        Scanner sc = new Scanner(System.in);
        String msg = sc.nextLine();
        byte[] sendData = msg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, 0, sendData.length, InetAddress.getLocalHost(), 9999);
        socket.send(sendPacket);

        socket.close();
    }
}
