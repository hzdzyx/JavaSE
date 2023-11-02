package com.cskaoyan.util;


import com.cskaoyan.controller.StudentController;

/**
 * 校验检查并处理用户输入数据,用户输入数据的规范是:
 *      1.学号必须是大于0的数字,且唯一
 *
 *
 * @since 15:38
 * @author wuguidong@com.cskaoyan.onaliyun.com
 */
public class CheckAndHandleUtils {
    // id需要校验重复性,所以需要依赖业务处理
    private final static StudentController studentController = new StudentController();


    private CheckAndHandleUtils() {
    }

    /*
        以下处理校验结果的方法,根据不同的错误状态,按照不同的方式进行处理
        但如果通过校验,则不会进行任何处理(因为不需要)
        方法会给出布尔类型返回值,方便进行业务处理:
            true表示数据是不合法的,需要进行处理
            false表示数据合法,无需处理
         由于业务逻辑大同小异,不再一一注释方法了.
     */
    public static boolean CheckAndHandleStuId(String stuId) {
        switch (checkStuId(stuId)) {
            case 1:
                ShowWindowUtils.showWarning("学号不能为空！");
                return true;
            case 2:
                ShowWindowUtils.showWarning("学号重复！");
                return true;
            case 3:
                ShowWindowUtils.showWarning("学号非数字！");
                return true;
            case 4:
                ShowWindowUtils.showWarning("学号应当大于0！");
                return true;
            default:
                // 数据正常
                return false;
        }
    }

    // -------------------------------------------------------------------
    // 以下是校验各种属性的方法
    // -------------------------------------------------------------------

    /**
     * 校验学号,返回一个int状态:
     *      0: 表示正常,学号符合规则
     *      1: 表示学号不存在,为null或者是""
     *      2: 表示学号重复
     *      3: 表示学号不是数字
     *      4: 表示学号不是大于0的数字
     * @since 9:29
     * @param stuId 被校验的学号
     * @return int
     * @author wuguidong@com.cskaoyan.onaliyun.com
     */
    private static int checkStuId(String stuId) {
        if (stuId == null || "".equals(stuId)) {
            // 学号不存在
            return 1;
        }
        if (!judgeNum(stuId)) {
            // 不是纯数字
            return 3;
        }
        // 字符串转换成数字，然后判断大小
        int idNum = Integer.parseInt(stuId);
        if (idNum <= 0) {
            // 学号不大于0
            return 4;
        }
        // 校验重复性
        if (studentController.checkStuIdRepeat(stuId)) {
            // 学号重复
            return 2;
        }
        // 一切正常
        return 0;
    }

    /**
     * 校验传入的字符串，
     * 如果字符串是纯数字就返回true，不是返回false
     * @since 11:32
     * @param numStr 被校验的字符串
     * @return boolean
     * @author wuguidong@com.cskaoyan.onaliyun.com
     */
    private static boolean judgeNum(String numStr) {
        try {
            Integer.parseInt(numStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
