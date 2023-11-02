package com.cskaoyan.test;

import com.cskaoyan.model.Student;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 客户端
 * 向服务端发送Student数组对象
 *
 * @since 11:00
 * @author wuguidong@cskaoyan.onaliyun.com
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        Student[] STUDS = new Student[5];
        STUDS[0] = new Student("11", "李明", "男", "清华大学", "软件工程", "18", "武汉", "13817618878", "123@qq.com");
        STUDS[1] = new Student("2", "马南山", "男", "华中师范大学", "计算机科学与技术", "27", "武汉", "13827618878", "123@qq.com");
        STUDS[2] = new Student("4", "周杰伦", "男", "武汉大学", "计算机科学与技术", "16", "武汉", "13837618878", "888@qq.com");
        STUDS[3] = new Student("13", "周杰", "女", "华中科技大学", "软件工程", "22", "武汉", "13747618878", "123@qq.com");
        STUDS[4] = new Student("3", "陈奕迅", "男", "清华大学", "计算机科学与技术", "33", "武汉", "13833618878", "123@qq.com");
        objectOutputStream.writeObject(STUDS);
        socket.close();
    }
}
