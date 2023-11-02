package com.cskaoyan.controller;

import com.cskaoyan.model.Student;
import com.cskaoyan.service.StudentService;
import com.cskaoyan.service.impl.StudentServiceImpl;

/**
 * view层请求controller层
 * 该类负责对view层的和Student学生操作相关的请求进行处理
 * 需要将请求分发给service层来进行业务逻辑处理
 *
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 * @since 19:30
 */
public class StudentController {

    // 控制层调用业务层
    private StudentService studentService = new StudentServiceImpl();

    /**
     * 学生信息会以一个表格的形式进行展示
     * 其中的表格数据全部来自于一个封装了学生信息的二维数据
     * 这个二维数组的长度是学生的个数
     * 这个二维数组中的每一个一维数组都代表一条学生信息
     * 这里直接调用service层获取封装好的String二维数组
     *
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 17:03
     */
    public String[][] getAllTableData() {
        return studentService.getAllTableData();
    }

    /**
     * 学生信息会以一个表格的形式进行展示
     * 其中的表头信息(也就是名字,学校,年龄等信息)来自一个String数组
     * 该String数组实际上就表示表格展示学生数据的顺序
     * 这里直接调用service层获取封装好的String一维数组
     *
     * @return java.lang.String[]
     * @author wuguidong@com.com.cskaoyan.onaliyun.com
     * @since 9:20
     */
    public String[] getTableColumns() {
        return studentService.getTableColumns();
    }

    /**
     * 检查id是否重复,true表示重复,否则为不重复
     * @since 18:11
     * @param id 输入的学生id,必然不为null和空字符串
     * @return boolean
     */
    public boolean checkStuIdRepeat(String id) {
        return studentService.checkStuIdRepeat(id);
    }

    /**
     * 插入一条学生信息，true表示成功，false为失败
     * 由于已经判定过很多次了，所以失败只有一种可能，即数组满了
     *
     * @param stu GUI界面输入的学生对象
     * @return boolean
     * @author wuguidong@com.com.cskaoyan.onaliyun.com
     * @since 14:21
     */
    public boolean addStudent(Student stu) {
        return studentService.addStudent(stu);
    }

    /**
     * 根据界面传入的id来删除一条学生信息,true表示删除成功,否者为删除失败
     * 实际上由于GUI图形界面已经校验过了,此id的Student对象必然存在
     *
     * @since 9:21
     * @param id 待删除学生的id
     * @return boolean
     * @author wuguidong@com.cskaoyan.onaliyun.com
     */
    public boolean delStudent(String id) {
        return studentService.delStudent(id);
    }

    /**
     * 通过目标学号id去数据源查找一条学生信息,有两种可能性:
     * 1.如果没有目标学生,则方法返回null
     * 2.如果存在目标学生,则需要返回一个封装学生信息的长度为1的String二维数组
     * 注: String二维数组的封装方式和stage2一致,只不过长度固定为1
     *
     * @since 20:35
     * @param stuId 查询使用的学号
     * @return java.lang.String[][]
     * @author wuguidong@com.cskaoyan.onaliyun.com
     */
    public String[][] getResultByStuId(String stuId) {
        // 调用service层来查找目标学生,service层还负责封装String二维数组
        return studentService.getResultByStuId(stuId);
    }

    /**
     * 通过目标姓名name去数据源查找学生信息,有两种可能性:
     * 1.如果完全没有目标学生,则方法返回null
     * 2.如果存在目标学生,则需要返回一个封装学生信息的长度为1的String二维数组
     * 注: String二维数组的封装方式和stage2一致,但name允许重复,所以有可能查找到多条学生信息!
     *
     * @since 12:10
     * @param name 被查找的目标name
     * @return java.lang.String[][]
     * @author wuguidong@com.cskaoyan.onaliyun.com
     */
    public String[][] getResultByName(String name) {
        // 调用service层来查找目标学生,service层还负责封装String二维数组
        return studentService.getResultByName(name);
    }
}
