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
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 表格列数据
        COLUMNS = new String[]{"学号", "姓名", "性别", "学校", "专业", "年龄", "城市", "手机号", "电子邮箱"};
    }

    // 提供给view层刷新表格时,重新读取文件使用,勿动!
    public static void refreshFileData() {
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    private static void init() throws IOException {
        // TODO 待实现
        // 1,创建文件对象
        File file = FileUtils.studentsFile;
        // 2,创建字符缓冲输入流对象
        BufferedReader br = new BufferedReader(new FileReader(file));
        // 3,读取数据
        String line;
        int index = 0;
        while ((line = br.readLine()) != null) {
            // 4,解析数据
            String[] split = line.split(",");
            // 5,创建学生对象
            Student student = new Student();
            student.setStuId(split[0].replaceAll("stuId=",""));
            student.setName(split[1].replaceAll("name=",""));
            student.setGender(split[2].replaceAll("gender=",""));
            student.setSchool(split[3].replaceAll("school=",""));
            student.setMajor(split[4].replaceAll("major=",""));
            student.setAge(split[5].replaceAll("age=",""));
            student.setCity(split[6].replaceAll("city=",""));
            student.setPhone(split[7].replaceAll("phone=",""));
            student.setEmail(split[8].replaceAll("email=",""));
            // 6,将学生对象存入数组
            STUDS[index++] = student;
        }
        // 7,释放资源
        br.close();
    }
}
