package com.slxy.terweb.controller;


import com.slxy.terweb.model.Teacher;
import com.slxy.terweb.service.ICollegeService;
import com.slxy.terweb.service.ITeacherService;
import com.slxy.terweb.service.IUserService;
import com.slxy.terweb.util.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.slxy.terweb.util.CommonUtils.disposeMVValue;

/**
 * @program: TeacherWeb
 * @description:判断用户是否有登录权限及相关操作
 * @author: Mr.Jiang
 * @create: 2019-04-15 20:58
 **/

@Operation(name = "用户操作")
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    public ICollegeService collegeService;

    @Autowired
    private ITeacherService teacherService;

    @Operation(name = "登录")
    @RequestMapping("login")
    public ModelAndView login(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        return userService.login(request, username, password);
    }


    @RequestMapping("password")
    public ModelAndView password(HttpServletRequest request){
        ModelAndView mv = disposeMVValue(request);
        mv.setViewName("common/changePwd");
        return mv;
    }

    @Operation(name = "更改密码")
    @RequestMapping("changePassword")
    public ModelAndView changePassword(HttpServletRequest request, String oldPassword, String newPassword, Model model)  {
        ModelAndView mv = disposeMVValue(request);
        String grade = (String) request.getSession().getAttribute("grade");
        //该用户为管理员
        if(grade != "null" && grade != null){
            userService.changeAdminPassword(request, oldPassword, newPassword, model);
        }else {
            userService.changeTeacherPassword(request, oldPassword, newPassword, model);
        }
        mv.setViewName("common/changePwd");
        return mv;
    }

    @Operation(name = "退出登录")
    @RequestMapping("exitLogin")
    public String exitLogin(){
        return "login";
    }

    @RequestMapping("myInformation")
    public ModelAndView myInformation(HttpServletRequest request){
        ModelAndView mv = disposeMVValue(request);
        //获取当前教师信息
        String Tsn = (String) request.getSession().getAttribute("username");
        Teacher teacher = teacherService.selectByTsn(Tsn);
        mv.addObject("teacher",teacher);
        mv.setViewName("common/personalInfo");
        return mv;
    }
}

