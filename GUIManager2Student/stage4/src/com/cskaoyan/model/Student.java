package com.cskaoyan.model;

/**
 * 学生实体类
 * @since 10:00
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class Student {
    // 学号
    private String stuId;
    // 姓名
    private String name;
    // 性别
    private String gender;
    // 学校
    private String school;
    // 专业
    private String major;
    // 年龄
    private String age;
    // 城市
    private String city;
    // 电话
    private String phone;
    // 邮箱
    private String email;

    public Student(String stuId, String name, String gender, String school, String major, String age, String city, String phone, String email) {
        this.stuId = stuId;
        this.name = name;
        this.gender = gender;
        this.school = school;
        this.major = major;
        this.age = age;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    // 除了学号id其余属性都需要G/S方法
    public String getStuId() {
        return stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // TODO 待重写比较对象相等的方法
}
