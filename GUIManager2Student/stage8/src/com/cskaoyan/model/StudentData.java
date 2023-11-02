package com.cskaoyan.model;


/**
 * 模拟学生数据源
 * 在stage8中,数据源的初始化需要通过网络传输来完成
 * 所以数据的初始化工作不在本类中完成,而是让view层去调用controller
 *
 * @since 14:09
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class StudentData {
    public static Student[] STUDS = new Student[20];
    public static String[] COLUMNS;

    static {
        // 表格列数据
        COLUMNS = new String[]{"学号", "姓名", "性别", "学校", "专业", "年龄", "城市", "手机号", "电子邮箱"};
    }
}
