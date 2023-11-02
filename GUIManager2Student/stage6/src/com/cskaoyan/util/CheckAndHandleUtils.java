package com.cskaoyan.util;


import com.cskaoyan.controller.StudentController;

/**
 * 校验检查并处理用户输入数据,用户输入数据的规范是:
 *      1.学号必须是大于0的数字,且唯一
 *      2.姓名的字符串长度必须大于1并且小于等于5
 *      3.性别只允许从“男”，“女”中选择
 *      5.年龄必须大于0且小于等于150
 *      6.手机号的长度必须是11位，且必须以“138”，“136”，“137”，“135”开头
 *      7.邮箱中必须带有"@",并且不能是开头和结尾
 *      8.学校、专业允许不填，其它为必填项。
 *
 * @since 15:38
 * @author wuguidong@cskaoyan.onaliyun.com
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

    public static boolean CheckAndHandleName(String name) {
        switch (checkName(name)) {
            case 1:
                ShowWindowUtils.showWarning("姓名不能为空！");
                return true;
            case 2:
                ShowWindowUtils.showWarning("姓名的长度应该在2-5之间！");
                return true;
            default:
                // 数据正常
                return false;
        }
    }

    public static boolean CheckAndHandleAge(String age) {
        switch (checkAge(age)) {
            case 1:
                ShowWindowUtils.showWarning("年龄不能为空！");
                return true;
            case 2:
                ShowWindowUtils.showWarning("年龄非数字！");
                return true;
            case 3:
                ShowWindowUtils.showWarning("年龄必须在1-150之间！");
                return true;
            default:
                // 数据正常
                return false;
        }
    }

    public static boolean CheckAndHandleCity(String city) {
        if (!checkCity(city)) {
            // 表示城市为空
            ShowWindowUtils.showWarning("必须输入城市！");
            return true;
        }
        // 数据正常
        return false;
    }

    public static boolean CheckAndHandlePhoneNum(String phoneNum) {
        switch (checkPhoneNum(phoneNum)) {
            case 1:
                ShowWindowUtils.showWarning("手机号必须非空！");
                return true;
            case 2:
                ShowWindowUtils.showWarning("手机号的格式不正确！");
                return true;
            default:
                // 数据正常
                return false;
        }
    }

    public static boolean CheckAndHandleEmail(String email) {
        switch (checkEmail(email)) {
            case 1:
                ShowWindowUtils.showWarning("邮箱不能为空！");
                return true;
            case 2:
                ShowWindowUtils.showWarning("邮箱格式错误！");
                return true;
            default:
                // 数据正常
                return false;
        }
    }

    public static boolean CheckAndHandleGender(String gender) {
        if (!checkGender(gender)) {
            // 数据不合法
            ShowWindowUtils.showWarning("性别格式错误！");
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------
    // 以下是校验各种属性的方法
    // -------------------------------------------------------------------

    /**
     * 校验id,返回一个int状态:
     *      0: 表示正常,学号符合规则
     *      1: 表示学号不存在,为null或者是""
     *      2: 表示学号重复
     *      3: 表示学号不是数字
     *      4: 表示学号不是大于0的数字
     * @since 9:29
     * @param stuId 被校验的学号
     * @return int
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private static int checkStuId(String stuId) {
        if (stuId == null || "".equals(stuId)) {
            // 学号不存在
            return 1;
        }
        int idNum;
        try {
            idNum = Integer.parseInt(stuId);
        } catch (NumberFormatException e) {
            // 输入不是数字
            return 3;
        }
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
     * 校验用户输入name,返回一个int状态值:
     *      0: 一切正常
     *      1: name为空
     *      2: name的长度不在2-5之内
     * @since 9:42
     * @param name 被校验的名字
     * @return int
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private static int checkName(String name) {
        if (name == null || "".equals(name)) {
            return 1;
        }
        if (name.length() > 5 || name.length() < 2) {
            return 2;
        }
        // 正常
        return 0;
    }

    /**
     * 校验用户输入age,返回一个int状态值:
     *      0: 一切正常
     *      1: 输入为空
     *      2: 非数字
     *      3: 没有在1-150之间
     * @since 9:49
     * @param age 被校验的年龄
     * @return int
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private static int checkAge(String age) {
        if (age == null || "".equals(age)) {
            return 1;
        }
        int ageNum;
        try {
            ageNum = Integer.parseInt(age);
        } catch (NumberFormatException e1) {
            // 非数字
            return 2;
        }
        if (ageNum < 1 || ageNum > 150) {
            return 3;
        }
        // 正常
        return 0;
    }

    /**
     * 校验用户输入city,只需要校验非空,所以:
     *      true: 表示非空,能够使用
     *      false: 空,不能使用
     * @since 9:53
     * @param city 被校验的城市
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private static boolean checkCity(String city) {
        return city != null && !"".equals(city);
    }

    /**
     * 校验用户输入手机号,返回一个int状态值:
     *      0: 一切正常
     *      1: 输入为空
     *      2: 格式不正确
     * @since 9:56
     * @param phoneNum 被校验的手机号
     * @return int
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private static int checkPhoneNum(String phoneNum) {
        if (phoneNum == null || "".equals(phoneNum)) {
            return 1;
        }
        if (phoneNum.length() != 11 || !phoneNum.startsWith("138") && !phoneNum.startsWith("136") && !phoneNum.startsWith("135") && !phoneNum.startsWith("137")) {
            return 2;
        }
        return 0;
    }

    /**
     * 校验用户输入邮箱,返回一个int状态值:
     *      0: 一切正常
     *      1: 输入为空
     *      2: 格式不正确
     * @since 10:00
     * @param email 被校验的邮箱
     * @return int
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private static int checkEmail(String email) {
        if (email == null || "".equals(email)) {
            return 1;
        }
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            return 2;
        }
        return 0;
    }

    /**
     * 性别只能是两个选项: 男和女
     * 校验通过返回true,否者返回false
     *
     * @since 21:05
     * @param gender 被校验的性别
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private static boolean checkGender(String gender) {
        if (gender == null || "".equals(gender)) {
            return false;
        }
        // 非男非女,格式错误
        return "男".equals(gender) || "女".equals(gender);
    }
}
