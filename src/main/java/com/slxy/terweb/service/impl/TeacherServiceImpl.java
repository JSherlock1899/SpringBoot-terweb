package com.slxy.terweb.service.impl;

import com.slxy.terweb.mapper.TeacherMapper;
import com.slxy.terweb.model.*;
import com.slxy.terweb.model.excel.*;
import com.slxy.terweb.service.ITeacherService;
import com.slxy.terweb.util.CommonUtils;
import com.slxy.terweb.util.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

import static com.slxy.terweb.util.ExcelUtils.exportExcelUtil;


@Service
public class TeacherServiceImpl implements ITeacherService {

    @Autowired
    TeacherMapper teacherMapper;


    @Override
//    @Cacheable(cacheNames = "terProject")
    public List<Project> selectProject(String tsn) {
        return teacherMapper.selectProject(tsn);
    }

    @Override
//    @Cacheable(cacheNames = "terPaper")
    public List<Paper> selectPaper(String tsn) {
        return teacherMapper.selectPaper(tsn);
    }

    @Override
//    @Cacheable(cacheNames = "terHonor")
    public List<Honor> selectHonor(String tsn) {
        return teacherMapper.selectHonor(tsn);
    }

    @Override
//    @Cacheable(cacheNames = "terPatent")
    public List<Patent> selectPatent(String tsn) {
        return teacherMapper.selectPatent(tsn);
    }

    @Override
//    @Cacheable(cacheNames = "terOther")
    public List<Other> selectOther(String tsn) {
        return teacherMapper.selectOther(tsn);
    }

    /**
     * 根据教师号导出个人的excel
     * @param tsn
     * @return
     */
    @Override
    public void exportProject(HttpServletResponse response, String tsn) {
        ExportExcel<ExcelProject> ex = new ExportExcel<ExcelProject>();
        CommonUtils util = new CommonUtils();
        String[] headers = { "项目编号", "项目名称", "教师工号","负责人","项目成员","科研状态","级别", "类型","经费", "立项时间", "结题时间","合同类型","所属学院","所属专业","学历","职称","备注" };
        List<ExcelProject> datalist = teacherMapper.exportProject(tsn);
        OutputStream out = null;
        exportExcelUtil(response, ex, util, datalist, headers,"项目");
    }

    @Override
    public void exportPaper(HttpServletResponse response, String tsn) {
        ExportExcel<ExcelPaper> ex = new ExportExcel<ExcelPaper>();
        CommonUtils util = new CommonUtils();
        List<ExcelPaper> datalist = teacherMapper.exportPaper(tsn);
        String[] headers =  { "检索号", "论文名", "教师工号","第一作者", "通讯作者","发表期刊", "发表时间", "级别","期卷页","依托项目","所属学院","所属专业","学历","职称", "备注" };
        exportExcelUtil(response, ex, util, datalist, headers,"论文");
    }

    @Override
    public void exportHonor(HttpServletResponse response,String tsn) {
        ExportExcel<ExcelHonor> ex = new ExportExcel<ExcelHonor>();
        CommonUtils util = new CommonUtils();
        List<ExcelHonor> datalist = teacherMapper.exportHonor(tsn);
        String[] headers =  { "编号", "名称", "教师工号","第一完成人","第一完成单位", "时间", "颁奖单位", "级别", "所属学院","所属专业","学历","职称","备注" };
        exportExcelUtil(response, ex, util, datalist, headers,"荣誉");
    }

    @Override
    public void exportPatent(HttpServletResponse response,String tsn) {
        ExportExcel<ExcelPatent> ex = new ExportExcel<ExcelPatent>();
        CommonUtils util = new CommonUtils();
        List<ExcelPatent> datalist = teacherMapper.exportPatent(tsn);
        String[] headers = {"名称", "授权号", "申请时间", "授权时间", "类型", "发明人","教师工号", "专利权人", "所属学院", "所属专业", "学历", "职称", "备注"};
        exportExcelUtil(response, ex, util, datalist, headers,"专利");
    }

    @Override
    public void exportOther(HttpServletResponse response,String tsn) {
        ExportExcel<ExcelOther> ex = new ExportExcel<ExcelOther>();
        CommonUtils util = new CommonUtils();
        List<ExcelOther> datalist = teacherMapper.exportOther(tsn);
        String[] headers = {"名称","教师工号" ,"发表人", "类型","日期","发表单位","所属学院","所属专业","学历","职称","描述"};
        exportExcelUtil(response, ex, util, datalist, headers,"其他论文");
    }

    @Override
    public String selectCollegeByTsn(String tsn) {
        return teacherMapper.selectCollegeByTsn(tsn);
    }

    @Override
    public List<Teacher> selectAll(String cname, String dname, String tname) {
        return teacherMapper.selectAll(cname,dname,tname);
    }

    @Override
    public int update(Teacher teacher) {
        return teacherMapper.update(teacher);
    }

    @Override
    public String getCsn(String Dname) {
        return teacherMapper.getCsn(Dname);
    }

    @Override
    public String getDsn(String Dname) {
        return teacherMapper.getDsn(Dname);
    }

    @Override
    public int importExcel(List<ExcelTeacher> list) {
        return teacherMapper.importExcel(list);
    }

    @Override
    public List<Teacher> selectAllUnaudit(String cname, String dname, String tname) {
        return teacherMapper.selectAllUnaudit(cname,dname,tname);
    }

    @Override
    public void pass(String majorkey, String messsage) {
        teacherMapper.pass(majorkey,messsage);
    }

    @Override
    public void nopass(String majorkey, String messsage) {
        teacherMapper.nopass(majorkey,messsage);
    }

    @Override
    public Teacher selectByTsn(String Tsn) {
        return teacherMapper.selectByTsn(Tsn);
    }

    @Override
    public int deleteByMajorkey(String majorkey) {
        return teacherMapper.deleteByMajorkey(majorkey);
    }

//    @Override
//    public ModelAndView findProject(HttpServletRequest request, Integer pn, Map<String, Object> map) {
//        String tsn = (String) request.getSession().getAttribute("username");
//        PageHelper.startPage(pn,5);
//        //当前条件下的查询结果
//        List<Project> projects = selectProject(tsn);
//        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
//        PageInfo pageInfo = new PageInfo<>(projects,5);
//        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
//        map.put("pageInfo",pageInfo);
//        ModelAndView mv = new ModelAndView();
//        mv.addAllObjects(map);
//        mv.addObject("projects",projects);
//        mv.setViewName("teacher/project/projectList");
//        return mv;
//    }


}
