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

    /**
     * 双击修改某个单元格的数据
     * 更新成功返回true,否则返回false
     *
     * 特别强调一下目标列这个参数:
     * 1. 目标列为0的话，一定是学号（当然学号不可修改，可以不做0的判断）
     * 2. 目标列为1的话，一定是姓名
     * 3. 目标列为2的话，一定是性别
     * ....
     * controller层可以直接依赖service来实现该功能
     *
     * @since 21:53
     * @param targetStuId 修改的Student信息的学号ID
     * @param targetCol 目标列
     * @param newValue 修改后的目标值
     * @return boolean
     */
    public boolean updateCellByStuId(String targetStuId, int targetCol, String newValue) {
        return studentService.updateStuFieldByStuId(targetStuId, targetCol, newValue);
    }

    /**
     * 通过目标id先找到要修改的目标Student对,然后用newStu替换它即可
     * 此方法返回一个int状态值表示结果:
     *      0: 表示成功修改
     *      1: newStu和原先对象一致,无需修改
     *      2: 未找到该学生,修改失败
     *
     * @since 12:19
     * @param targetStuId 修改的Student信息的学号ID
     * @param newStu view层传递过来的新的Student对象
     * @return boolean
     */
    public int updateStudentByStuId(String targetStuId, Student newStu) {
        return studentService.updateStudentByStuId(targetStuId, newStu);
    }

    /**
     * 按照学号的升序排序
     * 直接调用service层获取排序后的String二维数组
     *
     * @since 16:44
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    public String[][] ascendingSortById() {
        return studentService.get2DStrArrAscendingSortById();
    }

    /**
     * 按照年龄的降序排序
     * 直接调用service层获取排序后的String二维数组
     *
     * @since 16:44
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    public String[][] descendingSortByAge() {
        return studentService.get2DStrArrDescendingSortByAge();
    }

    /**
     * 所谓综合排序，就是按照以下规则的先后，来对学生信息进行排序：
     *
     * 1. 先将全体学生按照性别的男和女，总体分为两个部分（男或女在上面部分都可以）
     * 2. 在各自男女性别的内部，继续排序：
     *    1. 首先按照学校名字的长度升序排序。
     *    2. 如果他们学校名字的长度一致，就比较手机号的前三位，升序排列。
     *
     * 直接调用service层获取排序后的String二维数组
     * @since 16:44
     * @return java.lang.String[][]
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    public String[][] totalSort() {
        return studentService.get2DStrArrTotalSort();
    }

    /**
     * 调用service层来完成持久化学生信息的操作
     * 方法返回true表示保存成功,否则为保存失败
     *
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 10:36
     */
    public boolean saveDataToFile() {
        // TODO 待完成
        return studentService.saveDataToFile();
    }
}
