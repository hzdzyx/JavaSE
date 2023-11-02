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

    public String getStuId() {
        return stuId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public String getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

}
