package com.slxy.terweb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slxy.terweb.controller.base.BaseController;
import com.slxy.terweb.model.Condition;
import com.slxy.terweb.model.Project;
import com.slxy.terweb.model.excel.ExcelProject;
import com.slxy.terweb.service.IProjectService;
import com.slxy.terweb.util.CommonUtils;
import com.slxy.terweb.util.ExportExcel;
import com.slxy.terweb.util.Operation;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.slxy.terweb.util.CommonUtils.*;
import static com.slxy.terweb.util.ExcelUtils.excels;
import static com.slxy.terweb.util.ExcelUtils.exportExcelUtil;


/**
 * @description:项目模块
 * @author: Mr.Jiang
 **/
@Operation(name = "项目操作")
@Controller
@RequestMapping("project")
public class ProjectController extends BaseController<Project> {

    @Autowired
    IProjectService projectService;


    /**
     * 查询当前条件下的项目信息
     */
    @Operation(name="查询所有项目")
    @RequestMapping("selectAll")
    public ModelAndView findProject(HttpServletRequest request,@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pn,
                                    Map<String,Object> map, String cname, String dname, String starttime, String endtime, String tname){
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn,5);
        //当前条件下的查询结果
        List<Project> projects = projectService.selectAll(cname,dname,starttime,endtime,tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(projects,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        Condition condition = new Condition(cname,dname,starttime,endtime,tname);
        map.put("pageInfo",pageInfo);
        map.put("projects",projects);
        map.put("condition",condition);
        map.put("type","selectAll");
        mv.addAllObjects(map);
        mv.setViewName("admin/project/projectList");
        return mv;
    }


    /**
     * 导出当前条件下的项目信息
     */
    @Operation(name="导出当前条件下的项目信息")
    @RequestMapping("export")
    public void exportProject(HttpServletResponse response, String cname, String dname, String starttime, String endtime, String tname){
        ExportExcel<ExcelProject> ex = new ExportExcel<ExcelProject>();
        CommonUtils util = new CommonUtils();
        List<ExcelProject> datalist = projectService.selectExcel(cname,dname,starttime,endtime,tname);
        String[] headers = { "项目编号", "项目名称", "教师工号","负责人","项目成员","科研状态","级别", "类型","经费", "立项时间", "结题时间","合同类型","所属学院","所属专业","学历","职称","备注" };
        OutputStream out = null;
        exportExcelUtil(response, ex, util, datalist, headers,"项目");
    }

    /**
     * 查询未审核的项目信息
     */
    @Operation(name="查询未审核的项目信息")
    @RequestMapping("audit")
    public ModelAndView findAudit(HttpServletRequest request,@RequestParam(required = false,defaultValue = "1",value = "pn")Integer pn,
                                  Map<String,Object> map, String cname, String dname, String starttime, String endtime, String tname){
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn,5);
        //当前条件下的查询结果
        List<Project> projects = projectService.selectAllUnaudit(cname,dname,starttime,endtime,tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(projects,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        map.put("projects",projects);
        map.put("pageInfo",pageInfo);
        Condition condition = new Condition(cname,dname,starttime,endtime,tname);
        map.put("condition",condition);
        map.put("type","audit");
        mv.addAllObjects(map);
        mv.setViewName("admin/project/projectAudit");
        return mv;
    }

    /**
     * 跳转到详细信息审核页面
     */
    @Operation(name="跳转到详细信息审核页面")
    @RequestMapping("goDetail")
    public ModelAndView goDetail(HttpServletRequest request,String psn){
        ModelAndView mv = disposeMVValue(request);
        Project project = projectService.selectByMajorKey(psn);
        mv.addObject("project",project);
        mv.setViewName("admin/project/detail");
        return mv;
    }

    /**
     * 修改信息，重新提交待审核的项目信息
     */
    @Operation(name="修改信息，重新提交待审核的项目信息")
    @RequestMapping("updateOne")
    @ResponseBody
    public void updateOne(String Psn, String Pname, String Pmember, String Pgrad, String Pkind, String contractType, String Pmoney, String Pstatime, String Pcondition, String Pendtime, String Premarks) {
        String Paudit = "0";
        BigDecimal pmoney = new BigDecimal(Pmoney);
        Project project = new Project(Psn,Pname,Pmember,Pgrad,Pkind,pmoney,Pstatime,Pcondition,Pendtime,Premarks,contractType,Paudit);
        projectService.updateOne(project);
    }

    /**
     * 新建一条项目信息
     */
    @Operation(name="新建一条的项目信息")
    @RequestMapping(value = "insertOne", method = RequestMethod.POST)
    @ResponseBody
    public void insertOne(HttpServletRequest request, String Psn, String Pleader, String Pname, String Pmember, String Pgrad, String Pkind, String contractType, String Pmoney, String Pstatime, String Pendtime, String Pcondition, String Premarks){
        String Paudit = "0";
        BigDecimal pmoney = new BigDecimal(Pmoney);
        String Tsn = (String) request.getSession().getAttribute("username");
        Project project = new Project(Psn,Pleader,Pname,Pmember,Pgrad,Pkind,contractType,pmoney,Pstatime,Pendtime,Pcondition,Premarks,Tsn,Paudit);
        projectService.insertOne(project);
    }

    /**
     * 审核通过
     */
    @Operation(name="审核通过了一条信息")
    @RequestMapping("pass")
    @ResponseBody
    public void pass(String majorkey,String message){
        projectService.pass(majorkey,message);
    }

    /**
     * 审核不通过
     */
    @Operation(name="审核不通过了一条信息")
    @RequestMapping("nopass")
    @ResponseBody
    public void nopass(String majorkey,String message){
        projectService.nopass(majorkey,message);
    }


    /**
     * 统计图
     * @param request
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param cname 学院名
     * @return
     */
    @Operation(name="查看统计图")
    @RequestMapping("statistics")
    public ModelAndView projectStatistics(HttpServletRequest request, @RequestParam(required = false,defaultValue = "null")String starttime, @RequestParam(required = false,defaultValue = "null")String endtime, @RequestParam(required = false,defaultValue = "null")String cname){
        ModelAndView mv = projectService.statistic(request, cname, starttime, endtime);
        return mv;
    }



    @Operation(name="导入excel")
    @RequestMapping("importExcel")
    public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response, Model model,ModelAndView mv) throws IOException, FileUploadException{
        List list = excels(response,request);
        //记录当前行数
        int count = 0;
        List<ExcelProject> excels = new ArrayList<>();
        //12为每条记录的字段数
        for (int i=0;i<list.size();i=i+12) {
            count++;
            boolean vaild1 = judgeDate(list.get(i+8).toString());
            boolean vaild2 = judgeDate(list.get(i+9).toString());
            boolean isNumeric = isNumeric(list.get(i+7).toString());
            if (vaild1 == false || vaild2 == false || isNumeric == false) {
                count = count + 2;
                model.addAttribute("msg", "第" + count + "行可能存在错误，请检查后重新导入！");
                mv.setViewName("forward:/project/selectAll");
                return mv;
            }else {
                //分别为立项时间和结题时间
                String date1;
                String date2;
                BigDecimal money;
                if (!list.get(i+8).toString().equals("")){
                    date1 = formatDate(list.get(i+8).toString());
                }else {
                    date1 = list.get(i+8).toString();
                }
                if (!list.get(i+9).toString().equals("")){
                    date2 = formatDate(list.get(i+9).toString());
                }else {
                    date2 = list.get(i+9).toString();
                }
                if (!list.get(i+7).toString().equals("")){
                    money = new BigDecimal(list.get(i+7).toString());
                }else {
                    money = new BigDecimal("0");
                }
                try {
                    ExcelProject excel = new ExcelProject(list.get(i).toString(),list.get(i+1).toString(),list.get(i+2).toString(),list.get(i+3).toString(),list.get(i+4).toString(),list.get(i+5).toString(),list.get(i+6).toString(),money,date1,date2,list.get(i+10).toString(),list.get(i+11).toString());
                    excel.toString();
                    //逐个添加各条数据
                    excels.add(excel);
                }catch (Exception e){
                    e.printStackTrace();
                    model.addAttribute("msg", "请检查导入数据是否正确！");
                }
            }
        }
        try {
            int result = projectService.importExcel(excels);
            if (result == count){
                model.addAttribute("msg", "导入成功！");
            }
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            model.addAttribute("msg", "导入失败，请检查项目编号是否输入正确或是否存在重复导入！");
        }catch (NumberFormatException e){
            e.printStackTrace();
            model.addAttribute("msg", "导入失败，请检查第" + count + "行的经费项的格式是否正确！");
        }
        mv.setViewName("forward:/project/selectAll");
        return mv;
    }

    /**
     * 按主键删除对应数据
     */
    @Operation(name="删除了一条数据")
    @RequestMapping("delete")
    @ResponseBody
    public void delete(String majorkey){
        projectService.deleteByMajorkey(majorkey);
    }

    /**
     * 按主键修改对应数据
     */
    @Operation(name="修改了一条数据")
    @RequestMapping("alter")
    @ResponseBody
    public void alter(String Psn, String Pname, String Pmember, String Pgrad, String Pkind, String contractType, String Pmoney, String Pstatime, String Pcondition, String Pendtime, String Premarks) {
        String Paudit = "1";
        BigDecimal pmoney = new BigDecimal(Pmoney);
        Project project = new Project(Psn,Pname,Pmember,Pgrad,Pkind,pmoney,Pstatime,Pcondition,Pendtime,Premarks,contractType,Paudit);
        projectService.updateOne(project);
    }
}
