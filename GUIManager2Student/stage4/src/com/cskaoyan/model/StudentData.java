package com.cskaoyan.model;


/**
 * 模拟学生数据源
 *
 * @since 14:09
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class StudentData {
    public static final Student[] STUDS = new Student[20];
    public static final String[] COLUMNS;
    static {
        // 初始化
        init();
        // 表格列数据
        COLUMNS = new String[]{"学号", "姓名", "性别", "学校", "专业", "年龄", "城市", "手机号", "电子邮箱"};
    }

    /**
     * 初始化数据源数据
     * @since 9:27
     * @author wuguidong@com.com.cskaoyan.onaliyun.com
     */
    private static void init() {
        // 插入学生对象
        STUDS[0] = new Student("1", "李明", "男", "北京大学", "软件工程", "18", "武汉", "13817618878", "123@qq.com");
        STUDS[1] = new Student("2", "马铭", "男", "华中师范大学", "计算机科学与技术", "27", "武汉", "13827618878", "123@qq.com");
        STUDS[2] = new Student("3", "周杰伦", "男", "华中科技大学", "计算机科学与技术", "16", "武汉", "13837618878", "888@qq.com");
        STUDS[3] = new Student("4", "周杰", "男", "华中科技大学", "软件工程", "33", "武汉", "13847618878", "123@qq.com");
        STUDS[4] = new Student("5", "陈奕迅", "男", "清华大学", "计算机科学与技术", "17", "武汉", "13833618878", "123@qq.com");
        STUDS[5] = new Student("6", "李小兰", "女", "清华大学", "计算机科学与技术", "16", "武汉", "13844328878", "222@qq.com");
        STUDS[6] = new Student("7", "苏铭", "男", "复旦大学", "软件工程", "43", "深圳", "13523281231", "555@qq.com");
        STUDS[7] = new Student("8", "萧炎", "男", "武汉大学", "机械工程", "9", "武汉", "13713137631", "123@qq.com");
        STUDS[8] = new Student("9", "肖然", "男", "武汉大学", "土木工程", "22", "北京", "13512381293", "123@qq.com");
        STUDS[9] = new Student("10", "上官若", "男", "清华大学", "计算机科学与技术", "31", "长沙", "13712383128", "666@qq.com");
        STUDS[10] = new Student("11", "李明", "男", "武汉理工大学", "计算机科学与技术", "46", "长沙", "13687123731", "333@qq.com");
    }
}
