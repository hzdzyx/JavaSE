package com.cskaoyan.dao;

import com.cskaoyan.model.Student;

/**
 * 这个接口中定义了和Student实体类相关的CRUD操作
 * @since 14:21
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public interface StudentDao {
    /**
     * 直接从数据源中获取表头数据String数组
     * @return java.lang.String[]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 17:14
     */
    String[] getTableColumns();

    /**
     * 将数据源数组中的null元素排除
     * 返回仅装有Student对象的Student对象数组
     *
     * @return com.cskaoyan.model.Student[]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 17:27
     */
    Student[] getAllRealStudents();

    /**
     * 向数据源Student数组新增一条记录
     * 实际上是将数据源数组的一个null位置赋值为一个对象
     *
     * @param stu 被插入的Student对象
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 11:11
     */
    boolean addStudent(Student stu);

    /**
     * 删除数据源数组中的一条记录
     * 实际上就是找到该Studnet对象,然后赋值为null
     *
     * @param id 被删除Student对象的id
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 15:34
     */
    boolean delStudent(String id);

    /**
     * 在数据源中通过目标id查找目标Student对象
     * 如果找到就返回此对象
     * 否则返回null
     *
     * @param stuId 目标id
     * @return com.cskaoyan.model.Student
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 16:29
     */
    Student getStudentByStuId(String stuId);

    /**
     * 在数据源中通过目标name查找目标Student对象
     * 由于name不唯一,所以可能查到多个Student对象,返回Student对象数组
     * 如果没找到,直接返回null即可
     *
     * @param name 目标id
     * @return com.cskaoyan.model.Student
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 16:29
     */
    Student[] getStudentsByName(String name);
}
