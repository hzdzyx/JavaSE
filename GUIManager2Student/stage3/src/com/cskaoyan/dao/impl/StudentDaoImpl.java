package com.cskaoyan.dao.impl;

import com.cskaoyan.dao.StudentDao;
import com.cskaoyan.model.Student;
import com.cskaoyan.model.StudentData;

/**
 * service层依赖dao层进行数据处理,dao层直接和数据源交互
 * 该类负责和Student相关的数据处理
 * 该层仅负责将数据源中的数据提供给service层,该层不做数据校验以及处理
 *
 * @since 14:26
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class StudentDaoImpl implements StudentDao {
    // dao层直接依赖数据源,学生对象数组
    private Student[] STUDS = StudentData.STUDS;
    // dao层直接依赖数据源,表头数组String数组
    private String[] COLUMNS = StudentData.COLUMNS;

    @Override
    public Student[] getAllRealStudents() {
        // 获取数据
        // 确定不为null学生元素的个数
        int count = 0;
        for (Student stu : STUDS) {
            if (stu != null) {
                count++;
            }
        }
        Student[] result = new Student[count];
        int index = 0;
        for (Student stu : STUDS) {
            if (stu != null) {
                result[index] = stu;
                index++;
            }
        }
        return result;
    }

    @Override
    public String[] getTableColumns() {
        return COLUMNS;
    }

    @Override
    public boolean addStudent(Student stu) {
        for (int i = 0; i < STUDS.length; i++) {
            // 找到一个null的位置,赋值表示插入成功
            if (STUDS[i] == null) {
                STUDS[i] = stu;
                return true;
            }
        }
        // 没有null的位置,插入失败
        return false;
    }

    @Override
    public boolean delStudent(String id) {
        for (int i = 0; i < STUDS.length; i++) {
            if(STUDS[i].getStuId().equals(id)){
                STUDS[i]=null;
                return true;
            }
        }
        return false;
    }
}
