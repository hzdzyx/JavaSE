package com.cskaoyan.dao;

import com.cskaoyan.model.Student;

/**
 * 这个接口中定义了和Student实体类相关的CRUD操作
 * @since 14:21
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public interface StudentDao {
    boolean checkStuIdRepeat(String id);

    /**
     * 直接从数据源中获取表头数据String数组
     * @return java.lang.String[]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 17:14
     */
    String[] getTableColumns();

    String[][] getAllTableData();
    boolean addStudent(Student stu);
}
