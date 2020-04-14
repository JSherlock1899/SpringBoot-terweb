package com.slxy.terweb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slxy.terweb.model.*;
import com.slxy.terweb.model.excel.ExcelTeacher;
import com.slxy.terweb.service.*;
import com.slxy.terweb.util.Operation;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.slxy.terweb.util.CommonUtils.*;
import static com.slxy.terweb.util.ExcelUtils.excels;
/**
 * @description: 教师相关操作控制类
 * @author: Mr.Jiang
 **/
@Operation(name = "教师操作")
@Controller
@RequestMapping("teacher")
public class TeacherController{

    @Autowired
    ITeacherService teacherService;
    @Autowired
    IProjectService projectService;
    @Autowired
    IPaperService paperService;
    @Autowired
    IHonorService honorService;
    @Autowired
    IPatentService patentService;
    @Autowired
    IOtherService otherService;
    @Autowired
    public ICollegeService collegeService;

    /**
     * 查询教师个人的项目信息
     */
    @Operation(name = "查询教师个人的项目信息")
    @RequestMapping("findProject")
    public ModelAndView findProject(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer pageNum, ModelAndView mv){
        mv = disposeMVValue(request);
        String tsn = (String) request.getSession().getAttribute("username");
        PageHelper.startPage(pageNum,5);
        List<Project> projects = teacherService.selectProject(tsn);
        PageInfo pageInfo = new PageInfo<>(projects,5);
        mv.addObject("projects", projects);
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("tsn", tsn);
        mv.setViewName("teacher/project/projectList");
        return mv;
    }

    /**
     * 查询教师个人的论文信息
     */
    @Operation(name = "查询教师个人的论文信息")
    @RequestMapping("findPaper")
    public ModelAndView findPaper(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer pageNum, ModelAndView mv){
        mv = disposeMVValue(request);
        String tsn = (String) request.getSession().getAttribute("username");
        PageHelper.startPage(pageNum,5);
        //当前条件下的查询结果
        List<Paper> papers = teacherService.selectPaper(tsn);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(papers,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("papers",papers);
        mv.addObject("tsn", tsn);
        mv.setViewName("teacher/paper/paperList");
        return mv;
    }

    /**
     * 查询教师个人的荣誉信息
     */
    @Operation(name = "查询教师个人的荣誉信息")
    @RequestMapping("findHonor")
    public ModelAndView findHonor(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer pageNum, ModelAndView mv){
        mv = disposeMVValue(request);
        String tsn = (String) request.getSession().getAttribute("username");
        PageHelper.startPage(pageNum,5);
        //当前条件下的查询结果
        List<Honor> honors = teacherService.selectHonor(tsn);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(honors,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("honors",honors);
        mv.addObject("tsn", tsn);
        mv.setViewName("teacher/honor/honorList");
        return mv;
    }

    /**
     * 查询教师个人的专利信息
     */
    @Operation(name = "查询教师个人的专利信息")
    @RequestMapping("findPatent")
    public ModelAndView findPatent(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer pageNum, ModelAndView mv){
        mv = disposeMVValue(request);
        String tsn = (String) request.getSession().getAttribute("username");
        PageHelper.startPage(pageNum,5);
        //当前条件下的查询结果
        List<Patent> patents = teacherService.selectPatent(tsn);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(patents,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("patents",patents);
        mv.addObject("tsn", tsn);
        mv.setViewName("teacher/patent/patentList");
        return mv;
    }

    /**
     * 查询教师个人的其他成果信息
     */
    @Operation(name = "查询教师个人的其他成果信息")
    @RequestMapping("findOther")
    public ModelAndView findOther(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer pageNum, ModelAndView mv){
        mv = disposeMVValue(request);
        String tsn = (String) request.getSession().getAttribute("username");
        PageHelper.startPage(pageNum,5);
        //当前条件下的查询结果
        List<Other> others = teacherService.selectOther(tsn);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(others,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("others",others);
        mv.addObject("tsn", tsn);
        mv.setViewName("teacher/other/otherList");
        return mv;
    }

    /**
     * 查询单个项目的详细信息
     */
    @Operation(name = "查询单个项目的详细信息")
    @RequestMapping("goProjectDetail")
    public ModelAndView goProjectDetail(HttpServletRequest request,String psn){
        ModelAndView mv = disposeMVValue(request);
        Project project = projectService.selectByMajorKey(psn);
        mv = projectService.goDetail(request,project,mv);
        return mv;
    }


    /**
     * 查询单个论文的详细信息
     */
    @Operation(name = "查询单个论文的详细信息")
    @RequestMapping("goPaperDetail")
    public ModelAndView goPaperDetail(HttpServletRequest request,String pasearchnum){
        ModelAndView mv = disposeMVValue(request);
        Paper paper = paperService.selectByMajorKey(pasearchnum);
        paperService.goDetail(request,paper,mv);
        return mv;
    }

    /**
     * 查询单个荣誉的详细信息
     */
    @Operation(name = "查询单个荣誉的详细信息")
    @RequestMapping("goHonorDetail")
    public ModelAndView goHonorDetail(HttpServletRequest request,String hsn){
        ModelAndView mv = disposeMVValue(request);
        Honor honor = honorService.selectByMajorKey(hsn);
        mv = honorService.goDetail(request,honor,mv);
        return mv;
    }

    /**
     * 查询单个专利的详细信息
     */
    @Operation(name = "查询单个专利的详细信息")
    @RequestMapping("goPatentDetail")
    public ModelAndView goPatentDetail(HttpServletRequest request,String patsn){
        ModelAndView mv = disposeMVValue(request);
        Patent patent = patentService.selectByMajorKey(patsn);
        mv = patentService.goDetail(request,patent,mv);
        return mv;
    }

    /**
     * 查询单个其他成果的详细信息
     */
    @Operation(name = "查询单个其他成果的详细信息")
    @RequestMapping("goOtherDetail")
    public ModelAndView goOtherDetail(HttpServletRequest request,String otherName){
        ModelAndView mv = disposeMVValue(request);
        Other others = otherService.selectByMajorKey(otherName);
        mv = otherService.goDetail(request,others,mv);
        return mv;
    }


    /**
     * 导出教师个人的项目信息
     */
    @Operation(name = "导出教师个人的项目信息")
    @RequestMapping("exportProject")
    public void exportProject(HttpServletResponse response, String tsn){
        teacherService.exportProject(response,tsn);
    }

    /**
     * 导出教师个人的论文信息
     */
    @Operation(name = "导出教师个人的论文信息")
    @RequestMapping("exportPaper")
    public void exportPaper(HttpServletResponse response, String tsn){
        teacherService.exportPaper(response,tsn);
    }

    /**
     * 导出教师个人的荣誉信息
     */
    @Operation(name = "导出教师个人的荣誉信息")
    @RequestMapping("exportHonor")
    public void exportHonor(HttpServletResponse response, String tsn){
        teacherService.exportHonor(response,tsn);
    }

    /**
     * 导出教师个人的专利信息
     */
    @Operation(name = "导出教师个人的专利信息")
    @RequestMapping("exportPatent")
    public void exportPatent(HttpServletResponse response, String tsn){
        teacherService.exportPatent(response,tsn);
    }

    /**
     * 导出教师个人的其他成果信息
     */
    @Operation(name = "导出教师个人的其他成果信息")
    @RequestMapping("exportOther")
    public void exportOther(HttpServletResponse response, String tsn){
        teacherService.exportOther(response,tsn);
    }


    /**
     * 查询教师信息
     */
    @Operation(name = "查询教师信息")
    @RequestMapping("selectAll")
    public ModelAndView findTeacher(HttpServletRequest request,@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pn,
                                    Map<String,Object> map,String cname,String dname,String tname){
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn,5);
        //当前条件下的查询结果
        List<Teacher> teachers = teacherService.selectAll(cname,dname,tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(teachers,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        map.put("pageInfo",pageInfo);
        map.put("teachers",teachers);
        Condition condition = new Condition(cname,dname,null,null,tname);
        map.put("condition",condition);
        map.put("type","selectAll");
        mv.addAllObjects(map);
        mv.setViewName("admin/teacher/teacherList");
        return mv;
    }

    /**
     * 更改教师信息
     * @param Tsn 工号
     * @param Tname 教师名
     * @param Tsex 性别
     * @param Tdegree 学位
     * @param Tedubackground 学历
     * @param Tresdirection 研究方向
     * @param Tdateofbirth 出生年月
     * @param JobTitle 职称
     * @param Cname 所属学院
     * @param Dname 所属专业
     */
     @Operation(name = "更改教师信息")
     @RequestMapping("update")
     @ResponseBody
     public void update(String Tsn,String Tname,String Tsex,String Tdegree,String Tedubackground,String Tresdirection,String Tdateofbirth,String JobTitle,String Cname,String Dname){
        String Csn = teacherService.getCsn(Dname);
        String Dsn = teacherService.getDsn(Dname);
        Teacher teacher = new Teacher(Tsn,Tname,Tsex,Tdegree,Tedubackground,Tresdirection,Tdateofbirth,JobTitle,Csn,Dsn,Dname);
        teacherService.update(teacher);
     }


    @Operation(name = "导入excel")
    @RequestMapping("importExcel")
    public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response, Model model, ModelAndView mv) throws IOException, FileUploadException, ParseException {
        List list = excels(response,request);
        //记录当前行数
        int count = 0;
        List<ExcelTeacher> excels = new ArrayList<>();
        //10为每条记录的字段数
        for (int i=0;i<list.size();i=i+10) {
            count++;
            try {
                ExcelTeacher excel = new ExcelTeacher(list.get(i).toString(),list.get(i+1).toString(),list.get(i+2).toString(),list.get(i+3).toString(),list.get(i+4).toString(),list.get(i+5).toString(),list.get(i+6).toString(),list.get(i+7).toString(),list.get(i+8).toString(),list.get(i+9).toString());
                //逐个添加各条数据
                excels.add(excel);
            }catch (Exception e){
                e.printStackTrace();
                model.addAttribute("msg", "请检查导入数据是否正确！");
            }
        }
        try {
            int result = teacherService.importExcel(excels);
            if (result == count){
                model.addAttribute("msg", "导入成功！");            }
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            model.addAttribute("msg", "导入失败，请检查教师工号是否输入正确或是否存在重复导入！");
        }
        mv.setViewName("forward:/teacher/selectAll");
        return mv;
    }

    /**
     * 查询未审核的教师信息
     */
    @Operation(name = "查询未审核的教师信息")
    @RequestMapping("audit")
    public ModelAndView findAudit(HttpServletRequest request,@RequestParam(required = false,defaultValue = "1",value = "pn")Integer pn,
                                  Map<String,Object> map, String cname, String dname, String tname){
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn,5);
        //当前条件下的查询结果
        List<Teacher> teachers = teacherService.selectAllUnaudit(cname.trim(),dname,tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(teachers,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        map.put("pageInfo",pageInfo);
        mv.addAllObjects(map);
        mv.addObject("teachers",teachers);
        Condition condition = new Condition(cname,dname,tname);
        mv.addObject("condition",condition);
        mv.addObject("type","audit");
        mv.setViewName("admin/teacher/teacherAudit");
        return mv;
    }

    /**
     * 审核通过
     */
    @Operation(name = "审核通过一条教师信息")
    @RequestMapping("pass")
    @ResponseBody
    public void pass(String majorkey,String message) {
        teacherService.pass(majorkey,message);
    }

    /**
     * 审核不通过
     */
    @Operation(name = "审核不通过一条教师信息")
    @RequestMapping("nopass")
    @ResponseBody
    public void nopass(String majorkey,String message){
        teacherService.nopass(majorkey,message);
    }


    /**
     * 按主键删除对应数据
     */
    @Operation(name = "删除教师信息")
    @RequestMapping("delete")
    @ResponseBody
    public void delete(String majorkey){
        teacherService.deleteByMajorkey(majorkey);
    }
}
