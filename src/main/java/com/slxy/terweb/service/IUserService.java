package com.slxy.terweb.service;

import com.slxy.terweb.model.Admin;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: TeacherWeb
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-04-16 19:56
 **/


public interface IUserService {

    ModelAndView login(HttpServletRequest request, String username, String password);

    //管理员登录验证
    int adminLoginCheck(String username, String password);

    //教师登录验证
    int teacherLoginCheck(String username, String password);

    //获取管理员信息
    Admin getAdminGrade(String Aname);

    //获取教师名
    String getTeacherName(String Tsn);

    //判断原密码是否正确
    boolean verifyAdminPassword(String Aname, String oldPassword);

    //判断教师原密码是否正确
    boolean verifyTeacherPassword(String Tsn, String oldPassword);

    //修改管理员密码
    int alterAdminPassword(String Aname, String newPassword);

    //修改教师密码
    int alterTeacherPassword(String Tsn, String newPassword);

    //验证管理员密码修改并返回提示语
    void changeAdminPassword(HttpServletRequest request, String oldPassword, String newPassword, Model model);

    void changeTeacherPassword(HttpServletRequest request, String oldPassword, String newPassword, Model model);
}
