package com.slxy.terweb.service.impl;

import com.slxy.terweb.mapper.UserMapper;
import com.slxy.terweb.model.Admin;
import com.slxy.terweb.model.Condition;
import com.slxy.terweb.service.ICollegeService;
import com.slxy.terweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service("UserService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICollegeService collegeService;

    /**
     *
     * @param request
     * @param username
     * @param password
     * @return
     */
    @Override
    public ModelAndView login(HttpServletRequest request,  String username, String password) {
        ModelAndView mv = new ModelAndView();
        List<String> collegeList = collegeService.selectAllCollegeName();
        int result = userService.adminLoginCheck(username,password);
        //若result为1则该用户为管理员，若为0则用户不存在，继续判断该用户是否为教师
        if(result == 1) {
            Admin admin = userService.getAdminGrade(username);
            //管理员级别
            String grade = admin.getAgrad();
            String name = admin.getAname();
            request.getSession().setAttribute("grade", grade);
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("collegeList", collegeList);
            if ("1".equals(grade)){
                request.getSession().setAttribute("school", true);
                request.getSession().setAttribute("academy", false);
                mv.addObject("collegeList",collegeList);
                mv.addObject("school", true);
                mv.addObject("academy", false);
            } else if ("2".equals(grade)) {
                String cname = collegeService.getCname(admin.getCsn());
                request.getSession().setAttribute("cname", cname);
                request.getSession().setAttribute("school", false);
                request.getSession().setAttribute("academy", true);
                mv.addObject("school", false);
                mv.addObject("academy", true);
                mv.addObject("cname",cname);
            }
            mv.addObject("name",username);
            mv.addObject("admin",admin);
            mv.addObject("grade",grade);
            mv.addObject("type","selectAll");
            Condition condition = new Condition();
            mv.addObject("condition",condition);
            mv.setViewName("forward:/project/statistics");
            return mv;
        }else {
            result = userService.teacherLoginCheck(username,password);
            //若result为1则该用户为教师，反之则用户名或密码错误，跳转到 login.html页面重新登录
            if(result == 1) {
                //获取教师名
                String Tname = userService.getTeacherName(username);
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("name", Tname);
                request.getSession().setAttribute("grade", "null");
                mv.addObject("name", Tname);
                mv.setViewName("forward:/teacher/findProject");
                return mv;
            }else {
                mv.addObject("msg", "用户名或密码错误！");
                mv.setViewName("login");
                return mv;
            }
        }
    }

    @Override
    public int adminLoginCheck(String username,String password) {
        return userMapper.adminLoginCheck(username,password);
    }

    @Override
    public int teacherLoginCheck(String username,String password) {
        return userMapper.teacherLoginCheck(username,password);
    }

    @Override
    public Admin getAdminGrade(String Aname) {
        return userMapper.getAdminGrade(Aname);
    }

    @Override
    @Cacheable(value = "terName")
    public String getTeacherName(String Tsn) {
        return userMapper.getTeacherName(Tsn);
    }

    @Override
    public boolean verifyAdminPassword(String Aname, String oldPassword) {
        String password = userMapper.verifyAdminPassword(Aname,oldPassword);
        if(password == null){
            return false;
        }else {
            return password.trim().equals(oldPassword);
        }
    }

    @Override
    public boolean verifyTeacherPassword(String Tsn, String oldPassword) {
        String password = userMapper.verifyTeacherPassword(Tsn,oldPassword);
        return password.trim().equals(oldPassword);
    }

    @Override
    public int alterAdminPassword(String Aname, String newPassword) {
        return userMapper.alterAdminPassword(Aname,newPassword);
    }

    @Override
    public int alterTeacherPassword(String Tsn, String newPassword) {
        return userMapper.alterTeacherPassword(Tsn,newPassword);
    }


    @Override
    public void changeAdminPassword(HttpServletRequest request, String oldPassword, String newPassword, Model model) {
        String Aname = (String) request.getSession().getAttribute("username");
        boolean vaild;
        //判断原密码是否正确
        vaild = userService.verifyAdminPassword(Aname, oldPassword);
        //若密码验证成功则修改密码
        if (vaild) {
            int result = userService.alterAdminPassword(Aname, newPassword);
            if (result == 1) {
                model.addAttribute("msg", "修改密码成功！");
            }
        } else {
            model.addAttribute("msg", "原密码错误！");
        }
    }

    @Override
    public void changeTeacherPassword(HttpServletRequest request, String oldPassword, String newPassword, Model model) {
        //该用户为教师
        String Tsn = (String) request.getSession().getAttribute("username");
        Boolean vaild;
        vaild = userService.verifyTeacherPassword(Tsn, oldPassword);
        //若密码验证成功则修改密码
        if (vaild){
            int result = userService.alterTeacherPassword(Tsn, newPassword);
            if (result == 1) {
                model.addAttribute("msg", "修改密码成功！");
            }
        } else {
            model.addAttribute("msg", "原密码错误！");
        }
    }
}
