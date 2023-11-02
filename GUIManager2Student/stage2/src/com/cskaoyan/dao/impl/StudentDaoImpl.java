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
    public String[][] getAllTableData(){
        int i =0;
        for(Student student : STUDS){
            if(student!=null){
                i++;
            }
        }
        Student[] students = new Student[i];
        for (int j = 0; j < i; j++) {
            students[j]=STUDS[j];
        }
        String[][] result = new String[i][9];
        // result是String二维数组,其长度是Student对象数组students的长度
        for (int k = 0; k < result.length; k++) {
            result[k] = new String[]{
                    students[k].getStuId(),
                    students[k].getName(),
                    students[k].getGender(),
                    students[k].getSchool(),
                    students[k].getMajor(),
                    students[k].getAge(),
                    students[k].getCity(),
                    students[k].getPhone(),
                    students[k].getEmail()
            };
        }
        return result;
    }

    @Override
    public boolean addStudent(Student stu) {
        int count=0;
        for(Student student : STUDS){
            if(student!=null){
                count++;
            }
        }
        if(count<STUDS.length) {
            STUDS[count] = stu;
            return true;
        }
        return false;
    }

    @Override
    public boolean checkStuIdRepeat(String id) {
        for(Student student : STUDS){
            if(student!=null) {
                if (student.getStuId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String[] getTableColumns() {
        return COLUMNS;
    }
}
