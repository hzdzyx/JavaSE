package com.cskaoyan.service.impl;

import com.cskaoyan.dao.StudentDao;
import com.cskaoyan.dao.impl.StudentDaoImpl;
import com.cskaoyan.model.Student;
import com.cskaoyan.service.StudentService;

/**
 * controller层依赖service层完成业务逻辑处理
 * 该类负责对Student学生相关的业务逻辑的处理
 * 该层需要依赖dao层来获取数据然后处理数据,但该层不直接和数据源交互
 *
 * @since 10:18
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class StudentServiceImpl implements StudentService {
    // 业务处理需要获取数据,所以需要依赖数据处理层
    private StudentDao studentDao = new StudentDaoImpl();

    @Override
    public String[][] getAllTableData() {
        // 通过调用dao获取所有Student对象数组,不包含null元素
        Student[] allRealStudents = studentDao.getAllRealStudents();
        return get2DStrArrByStudentArr(allRealStudents);
    }

    @Override
    public String[] getTableColumns() {
        return studentDao.getTableColumns();
    }

    @Override
    public boolean checkStuIdRepeat(String id) {
        Student[] allRealStudents = studentDao.getAllRealStudents();
        for (Student realStudent : allRealStudents) {
            if (realStudent.getStuId().equals(id)) {
                // id存在
                return true;
            }
        }
        // id不存在
        return false;
    }

    @Override
    public boolean addStudent(Student stu) {
        return studentDao.addStudent(stu);
    }

    @Override
    public boolean delStudent(String id) {
        return studentDao.delStudent(id);
    }

    @Override
    public String[][] getResultByStuId(String stuId) {
        Student target = studentDao.getStudentByStuId(stuId);
        if (target == null) {
            // 未找到目标Student
            return null;
        }
        // 找到目标Student然后将其封装成String二维数组
        return get2DStrArrByStudent(target);
    }

    @Override
    public String[][] getResultByName(String name) {
        // 按照名字查找,完全可能查到多条数据,所以dao层的返回值是Student对象数组
        Student[] target = studentDao.getStudentsByName(name);
        if (target == null) {
            // 未找到目标Student
            return null;
        }
        return get2DStrArrByStudentArr(target);
    }

    /**
     * 将一个不含null元素的Student对象数组转换成表格需要的数据String二维数组
     *
     * @since 12:21
     * @param students 目标学生数组
     * @return java.lang.String[][]
     */
    private String[][] get2DStrArrByStudentArr(Student[] students) {
        // Student对象的个数就是二维数组的长度
        String[][] result = new String[students.length][];
        /*
            String二维数组中的每个一维数组数组的长度一定是表格的列数(实际上就是9)
            并且表格的每列数据都是固定的
            按照"学号、姓名、性别、学校、专业、年龄、城市、手机号、邮箱"排列
            于是只需要遍历二维数组,然后给其中的一维数组赋值即可
         */
        for (int i = 0; i < result.length; i++) {
            result[i] = new String[]{
                    students[i].getStuId(),
                    students[i].getName(),
                    students[i].getGender(),
                    students[i].getSchool(),
                    students[i].getMajor(),
                    students[i].getAge(),
                    students[i].getCity(),
                    students[i].getPhone(),
                    students[i].getEmail()
            };
        }
        return result;
    }

    /**
     * 将一个Student对象转换成表格需要的一条数据,即只有一个元素的二维数组
     *
     * @since 20:39
     * @param stu 目标学生对象
     * @return java.lang.String[][]
     * @author wuguidong@com.cskaoyan.onaliyun.com
     */
    private String[][] get2DStrArrByStudent(Student stu) {
        return new String[][]{{
                stu.getStuId(), stu.getName(), stu.getGender(), stu.getSchool(), stu.getMajor(), stu.getAge(), stu.getCity(), stu.getPhone(), stu.getEmail()}};
    }
}
