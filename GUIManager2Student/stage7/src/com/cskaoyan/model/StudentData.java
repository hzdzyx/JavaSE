package com.cskaoyan.model;


import com.cskaoyan.util.FileUtils;

import java.io.*;

/**
 * 模拟学生数据源
 *
 * @since 14:09
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class StudentData {
    public static Student[] STUDS = new Student[20];
    public static String[] COLUMNS;

    static {
        // 初始化
            init();
        // 表格列数据
        COLUMNS = new String[]{"学号", "姓名", "性别", "学校", "专业", "年龄", "城市", "手机号", "电子邮箱"};
    }

    // 提供给view层刷新表格时,重新读取文件使用,勿动!
    public static void refreshFileData() {
        init();
    }

    /**
     * 使用反序列流将students.txt文件中的Student数组对象读取出来
     * 然后将该数组中的Student对象赋值给数据源数组STUDS
     *
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 11:08
     */
    private static void init() {
        // 1,创建反序列流对象
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(FileUtils.studentsFile));
            // 2,读取文件中的Student数组对象
            Student[] students = (Student[]) ois.readObject();
            // 3,将该数组中的Student对象赋值给数据源数组STUDS
            STUDS = students;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 4,关闭流
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭流失败");
            }
        }
    }
}
