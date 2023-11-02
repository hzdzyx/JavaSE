package com.cskaoyan.model;

import com.cskaoyan.util.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
     * 从students.txt文件中将Student对象读出来,然后给STUDS数组中的元素赋值
     * 该txt文件中的一行数据就表示一个Student对象
     * 先读取一行数据,然后将数据中表示的各个属性的取值解析出来,创建相应Student对象
     * 最后将Student对象逐一存放入STUDS数组
     *
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 16:07
     */
    private static void init() {
        // TODO 待实现
    }
}
