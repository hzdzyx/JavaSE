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

    /**
     * 根据目标ID查到到目标Student
     * 然后根据目标列,找到要修改的属性,然后将newValue赋值给它即可
     *
     * @param targetStuId 修改的Student信息的学号ID
     * @param targetCol 目标列
     * @param newValue 修改后的目标值
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 11:43
     */
    boolean updateStuFieldByStuId(String targetStuId, int targetCol, String newValue);

    /**
     * 直接调用dao层实现以下功能:
     * 通过目标id先找到要修改的目标Student对,然后用newStu替换它即可
     * 此方法返回一个int状态值表示结果:
     *      0: 表示成功修改
     *      1: newStu和原先对象一致,无需修改
     *      2: 未找到该学生,修改失败
     * @param targetStuId 修改的Student信息的学号ID
     * @param newStu view层传递过来的新的Student对象
     * @return int
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 11:52
     */
    int updateStudentByStuId(String targetStuId, Student newStu);

    /**
     * 调用dao层获取所有Student对象的数组(排除null元素)
     * 然后按照ID升序对Student对象数组做自然排序
     * 最后将Student对象数组转换成String二维数组
     *
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 14:53
     */
    String[][] get2DStrArrAscendingSortById();

    /**
     * 调用dao层获取所有Student对象的数组(排除null元素)
     * 然后按照age降序对Student对象数组做排序(此时用带比较器的sort方法)
     * 最后将Student对象数组转换成String二维数组
     *
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 15:05
     */
    String[][] get2DStrArrDescendingSortByAge();

    /**
     * 调用dao层获取所有Student对象的数组(排除null元素)
     * 然后利用方法引用调用综合排序的已实现方法
     * 最后将Student对象数组转换成String二维数组
     *
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 15:35
     */
    String[][] get2DStrArrTotalSort();
}
