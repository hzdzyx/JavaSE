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
    public boolean updateCellByStuId(String stuId, int column, String newValue) {
        Student target = getStudentByStuId(stuId);
        if (target == null) {
            // 未找到目标学生
            return false;
        }
        // 找到目标学生,更新目标学生的信息
        switch (column) {
            case 1:
                target.setName(newValue);
                break;
            case 2:
                target.setGender(newValue);
                break;
            case 3:
                target.setSchool(newValue);
                break;
            case 4:
                target.setMajor(newValue);
                break;
            case 5:
                target.setAge(newValue);
                break;
            case 6:
                target.setCity(newValue);
                break;
            case 7:
                target.setPhone(newValue);
                break;
            case 8:
                target.setEmail(newValue);
                break;
            default:
                return false;
        }
        return true;
    }
    @Override
    public int updateStudentByStuId(String stuId, Student newStu) {
        Student target = getStudentByStuId(stuId);
        if (target == null) {
            // 未找到目标学生
            return 2;
        }
        // 找到目标学生,更新目标学生的信息
        if (target.equals(newStu)) {
            // 两个对象相等,无需修改
            return 1;
        }
        // 两个对象不相等,修改成功
        target.setName(newStu.getName());
        target.setGender(newStu.getGender());
        target.setSchool(newStu.getSchool());
        target.setMajor(newStu.getMajor());
        target.setAge(newStu.getAge());
        target.setCity(newStu.getCity());
        target.setPhone(newStu.getPhone());
        target.setEmail(newStu.getEmail());
        return 0;
    }
}
