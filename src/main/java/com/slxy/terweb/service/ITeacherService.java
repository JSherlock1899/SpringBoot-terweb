package com.slxy.terweb.service;

import com.slxy.terweb.model.*;
import com.slxy.terweb.model.excel.ExcelTeacher;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ITeacherService {

    List<Project> selectProject(String tsn);

    List<Paper> selectPaper(String tsn);

    List<Honor> selectHonor(String tsn);

    List<Patent> selectPatent(String tsn);

    List<Other> selectOther(String tsn);

    void exportProject(HttpServletResponse response, String tsn);

    void exportPaper(HttpServletResponse response, String tsn);

    void exportHonor(HttpServletResponse response,String tsn);

    void exportPatent(HttpServletResponse response,String tsn);

    void exportOther(HttpServletResponse response,String tsn);

    String selectCollegeByTsn(String tsn);

    //查询当前条件下的所有数据
    List<Teacher> selectAll(String cname, String dname, String tname);

    int update(Teacher teacher);

    //获取当前教师的学院号
    String getCsn(String Dname);

    //获取当前教师的专业号
    String getDsn(String Dname);

    //导入excel
    int importExcel(List<ExcelTeacher> list);

    //查询未审核的数据
    List<Teacher> selectAllUnaudit(String cname, String dname, String tname);

    //审核通过
    void pass(String majorkey, String messsage);

    //审核不通过
    void nopass(String majorkey, String messsage);

    //获取教师信息
    Teacher selectByTsn(String Tsn);

    //按主键删除信息
    int deleteByMajorkey(String majorkey);

    //查询教师个人的项目信息
//    ModelAndView findProject(HttpServletRequest request, Integer pn, Map<String,Object> map);
}
