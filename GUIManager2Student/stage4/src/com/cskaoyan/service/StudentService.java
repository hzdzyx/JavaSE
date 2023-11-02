package com.cskaoyan.service;

import com.cskaoyan.model.Student;

/**
 * 这个接口中定义了与Student相关业务逻辑操作
 * @since 11:29
 * @author wuguidong@cskaoyan.onaliyun.com
 */
public interface StudentService {

    /**
     * 通过调用dao层先获取所有的Student对象数组(不含null元素的数组)
     * 然后将Student对象数组按照规则转换成String二维数组
     * 转换的规则是:
     * 这个二维数组的长度是学生的个数
     * 这个二维数组中的每一个一维数组都代表一条学生信息
     * String二维数组中的每个一维数组数组的长度一定是表格的列数(实际上就是9)
     * 并且表格的每列数据都是固定的
     * 按照"学号、姓名、性别、学校、专业、年龄、城市、手机号、邮箱"排列
     *
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 17:16
     */
    String[][] getAllTableData();

    /**
     * 直接调用dao层获取表格的表头数据String数组
     * @return java.lang.String[]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 17:13
     */
    String[] getTableColumns();

    /**
     * 直接调用dao层获取所有的Student对象
     * 然后遍历逐一比对
     * @param id 被校验的ID
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 10:54
     */
    boolean checkStuIdRepeat(String id);

    /**
     * 直接调用dao层尝试插入向数据源数组插入Student对象
     * 实际上是将数据源数组的一个null位置赋值为一个对象
     * @param stu 被插入的Student对象
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 11:11
     */
    boolean addStudent(Student stu);

    /**
     * 直接调用dao层尝试删除数据源数组的目标id的Student对象
     * 实际上是将数据源数组的一个目标Student对象赋值为null
     * @param id 被删除Student对象的id
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 15:32
     */
    boolean delStudent(String id);

    /**
     * 首先调用dao层根据目标id查询Student信息
     * 如果没有查到,则直接返回null
     * 如果查到目标Student信息,则需要将该Student对象封装成String二维数组
     *
     * @param stuId 被查询的目标id
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 16:16
     */
    String[][] getResultByStuId(String stuId);

    /**
     * 首先调用dao层根据目标name查询Student信息
     * 如果没有查到,则直接返回null
     * 如果查到目标Student信息,则需要将该Student对象信息封装成String二维数组
     *
     * @param name 被查询的目标name
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 16:16
     */
    String[][] getResultByName(String name);

}
