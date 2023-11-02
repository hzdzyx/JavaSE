package com.cskaoyan.service.impl;

import com.cskaoyan.dao.StudentDao;
import com.cskaoyan.dao.impl.StudentDaoImpl;
import com.cskaoyan.model.Student;
import com.cskaoyan.service.StudentService;

import static com.cskaoyan.model.StudentData.STUDS;

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
        // TODO 待实现
        if (getResultByStuId(id)!=null){
            return studentDao.delStudent(id);
        }
        return false;
    }

    @Override
    public String[][] getResultByStuId(String stuId) {
        // TODO 待实现
        Student[] students = studentDao.getAllRealStudents();
        for (int i = 0; i < students.length; i++) {
            if (students[i].getStuId().equals(stuId)) {
                String[][] result = new String[1][];
                result[0] = new String[]{
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
                return result;
            }

        }
        return null;
    }

    @Override
    public String[][] getResultByName(String name) {
        // TODO 待实现
        Student[] students = studentDao.getAllRealStudents();
        int count = 0;
        for (Student student : students) {
            if (student.getName().contains(name)) {
                count++;
            }
        }
        String[][] result = new String[count][];
        int i = 0;
        for (int j = 0; j < students.length; j++) {
            if (students[j].getName().contains(name)) {
                result[i] = new String[]{
                        students[j].getStuId(),
                        students[j].getName(),
                        students[j].getGender(),
                        students[j].getSchool(),
                        students[j].getMajor(),
                        students[j].getAge(),
                        students[j].getCity(),
                        students[j].getPhone(),
                        students[j].getEmail()
                };
                i++;
            }
        }
        return result;
}

        /**
         * 将一个不含null元素的Student对象数组转换成表格需要的数据String二维数组
         *
         * @since 12:21
         * @param students 目标学生数组
         * @return java.lang.String[][]
         */
        private String[][] get2DStrArrByStudentArr (Student[]students){
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
    }
