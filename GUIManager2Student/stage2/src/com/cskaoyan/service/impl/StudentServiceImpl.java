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
        // TODO 待实现
        return studentDao.getAllTableData();
    }

    @Override
    public String[] getTableColumns() {
        return studentDao.getTableColumns();
    }

    @Override
    public boolean checkStuIdRepeat(String id) {
        // TODO 待实现
        return studentDao.checkStuIdRepeat(id);
    }

    @Override
    public boolean addStudent(Student stu) {
        // TODO 待实现
        return studentDao.addStudent(stu);
    }
}
