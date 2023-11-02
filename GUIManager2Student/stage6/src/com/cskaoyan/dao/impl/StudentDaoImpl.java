package com.cskaoyan.dao.impl;

import com.cskaoyan.dao.StudentDao;
import com.cskaoyan.model.Student;
import com.cskaoyan.model.StudentData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
            // 为了避免空指针异常,排除null
            if (STUDS[i] == null) {
                continue;
            }
            if (id.equals(STUDS[i].getStuId())) {
                STUDS[i] = null;
                // 删除成功
                return true;
            }
        }
        return false;
    }

    @Override
    public Student getStudentByStuId(String stuId) {
        // 去除null,避免空指针异常
        Student[] realStudents = getAllRealStudents();
        for (Student stu : realStudents) {
            if (stu.getStuId().equals(stuId)) {
                return stu;
            }
        }
        return null;
    }

    @Override
    public Student[] getStudentsByName(String name) {
        // 去除null,避免空指针异常
        Student[] realStudents = getAllRealStudents();
        int count = 0;
        for (Student stu : realStudents) {
            if (stu.getName().contains(name)) {
                count++;
            }
        }
        if (count == 0) {
            // 未找到学生
            return null;
        }
        // 根据目标Studnet对象的个数创建数组
        Student[] result = new Student[count];
        int index = 0;
        for (Student stu : realStudents) {
            if (stu.getName().contains(name)) {
                result[index] = stu;
                index++;
            }
        }
        return result;
    }

    @Override
    public boolean updateStuFieldByStuId(String targetStuId, int targetCol, String newValue) {
        // 需要遍历数据源数组
        for (int i = 0; i < STUDS.length; i++) {
            // 为了避免空指针异常,排除null
            if (STUDS[i] == null) {
                continue;
            }
            if (targetStuId.equals(STUDS[i].getStuId())) {
                // 利用switch判断要修改的目标属性
                switch (targetCol) {
                    case 1:
                        // 姓名
                        STUDS[i].setName(newValue);
                        break;
                    case 2:
                        // 性别
                        STUDS[i].setGender(newValue);
                        break;
                    case 3:
                        // 学校
                        STUDS[i].setSchool(newValue);
                        break;
                    case 4:
                        // 专业
                        STUDS[i].setMajor(newValue);
                        break;
                    case 5:
                        STUDS[i].setAge(newValue);
                        // 年龄
                        break;
                    case 6:
                        STUDS[i].setCity(newValue);
                        // 城市
                        break;
                    case 7:
                        STUDS[i].setPhone(newValue);
                        // 手机号
                        break;
                    case 8:
                        STUDS[i].setEmail(newValue);
                        // 电子邮箱
                        break;
                    default:
                        // 错误的列数,返回false
                        return false;
                }
                // 修改成功
                return true;
            }
        }
        // 修改失败
        return false;
    }

    @Override
    public int updateStudentByStuId(String targetStuId, Student stu) {
        // 需要遍历数据源数组
        for (int i = 0; i < STUDS.length; i++) {
            // 为了避免空指针异常,排除null
            if (STUDS[i] == null) {
                continue;
            }
            // 这里需要重写Studnet类的equals方法,只要属性取值一样就是相同的Student对象
            if (targetStuId.equals(STUDS[i].getStuId())) {
                if (STUDS[i].equals(stu)) {
                    // 说明未做任何修改,禁止修改
                    return 1;
                }
                // 找到后,直接替换
                STUDS[i] = stu;
                // 修改成功
                return 0;
            }
        }
        // 修改失败
        return 2;
    }
}
