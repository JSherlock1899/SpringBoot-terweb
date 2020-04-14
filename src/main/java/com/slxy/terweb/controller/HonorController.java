package com.slxy.terweb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slxy.terweb.controller.base.BaseController;
import com.slxy.terweb.model.Condition;
import com.slxy.terweb.model.Honor;
import com.slxy.terweb.model.excel.ExcelHonor;
import com.slxy.terweb.service.IHonorService;
import com.slxy.terweb.service.IUserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.slxy.terweb.util.CommonUtils.*;
import static com.slxy.terweb.util.ExcelUtils.excels;
import static com.slxy.terweb.util.ExcelUtils.exportExcelUtil;
/**
 * @program: TeacherWeb
 * @description: 荣誉模块
 * @author: Mr.Jiang
 * @create: 2019-04-21 16:26
 **/

@Operation(name = "荣誉操作")
@Controller
@RequestMapping("honor")
public class HonorController extends BaseController<Honor> {

    @Autowired
    IHonorService honorService;
    @Autowired
    IUserService userService;


    /**
     * 查询当前条件下的荣誉信息
     */
    @Operation(name="查询所有荣誉")
    @RequestMapping("selectAll")
    public ModelAndView findHonor(HttpServletRequest request,@RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pn,
                                  Map<String, Object> map, String cname, String dname, String starttime, String endtime, String tname) {
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn, 5);
        //当前条件下的查询结果
        List<Honor> honors = honorService.selectAll(cname, dname, starttime, endtime, tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(honors, 5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        Condition condition = new Condition(cname, dname, starttime, endtime, tname);
        map.put("pageInfo",pageInfo);
        map.put("condition",condition);
        map.put("type","selectAll");
        map.put("honors", honors);
        mv.addAllObjects(map);
        mv.setViewName("admin/honor/honorList");
        return mv;
    }

    /**
     * 查询未审核的荣誉信息
     */
    @Operation(name="查询未审核的荣誉信息")
    @RequestMapping("audit")
    public ModelAndView findAudit(HttpServletRequest request,@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                  Map<String, Object> map, String cname, String dname, String starttime, String endtime, String tname) {
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn, 5);
        //当前条件下的查询结果
        List<Honor> honors = honorService.selectAllUnaudit(cname, dname, starttime, endtime, tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(honors, 5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        map.put("honors", honors);
        map.put("pageInfo", pageInfo);
        Condition condition = new Condition(cname, dname, starttime, endtime, tname);
        map.put("condition", condition);
        map.put("type", "audit");
        mv.addAllObjects(map);
        mv.setViewName("admin/honor/honorAudit");
        return mv;
    }

    /**
     * 跳转到详细信息审核页面
     */
    @Operation(name="跳转到详细信息审核页面")
    @RequestMapping("goDetail")
    public ModelAndView goDetail(HttpServletRequest request, String hsn) {
        ModelAndView mv = disposeMVValue(request);
        Honor honor = honorService.selectByMajorKey(hsn);
        mv.addObject("honor", honor);
        mv.setViewName("admin/honor/detail");
        return mv;
    }

    /**
     * 修改信息，重新提交待审核的荣誉信息
     */
    @Operation(name="修改信息，重新提交待审核的荣誉信息")
    @RequestMapping("updateOne")
    @ResponseBody
    public void updateOne(String Hsn, String Hname, String Hwinner, String Hdate, String Hcompany, String department, String Hgrad, String Hremarks) {
        String Haudit = "0";
        Honor honor = new Honor(Hsn, Hname, Hwinner, Hdate, Hcompany, Hgrad, Hremarks,"", Haudit, department);
        honorService.updateOne(honor);
    }

    /**
     * 新建一条荣誉信息
     */
    @Operation(name="新建一条的荣誉信息")
    @RequestMapping(value = "insertOne", method = RequestMethod.POST)
    @ResponseBody
    public void insertOne(HttpServletRequest request, String Hname, String Hwinner, String Hdate, String Hcompany, String department, String Hgrad, String Hremarks) {
        String Haudit = "0";
        String Tsn = (String) request.getSession().getAttribute("username");
        Honor honor = new Honor(Hname, Hwinner, Hdate, Hcompany, Hgrad, Hremarks, Tsn, Haudit, department);
        honorService.insertOne(honor);
    }


    /**
     * 导出当前条件下的荣誉信息
     */
    @Operation(name="导出当前条件下的荣誉信息")
    @RequestMapping("export")
    public void export(HttpServletResponse response, String cname, String dname, String starttime, String endtime, String tname) {
        ExportExcel<ExcelHonor> ex = new ExportExcel<>();
        CommonUtils util = new CommonUtils();
        List<ExcelHonor> datalist = honorService.selectExcel(cname, dname, starttime, endtime, tname);
        String[] headers = {"编号", "名称", "教师工号", "第一完成人", "第一完成单位", "时间", "颁奖单位", "级别", "所属学院", "所属专业", "学历", "职称", "备注"};
        exportExcelUtil(response, ex, util, datalist, headers, "荣誉");
    }

    /**
     * 审核通过
     */
    @Operation(name="审核通过了一条信息")
    @RequestMapping("pass")
    @ResponseBody
    public void pass(String majorkey, String message) {
        honorService.pass(majorkey, message);
    }

    /**
     * 审核不通过
     */
    @Operation(name="审核不通过了一条信息")
    @RequestMapping("nopass")
    @ResponseBody
    public void nopass(String majorkey, String message) {
        honorService.nopass(majorkey, message);
    }


    /**
     * 统计图
     *
     * @param request
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @param cname     学院名
     * @return
     */
    @Operation(name="查看统计图")
    @RequestMapping("statistics")
    public ModelAndView honorStatistics(HttpServletRequest request, @RequestParam(required = false, defaultValue = "null") String starttime, @RequestParam(required = false, defaultValue = "null") String endtime, @RequestParam(required = false, defaultValue = "null") String cname) {
        ModelAndView mv = honorService.statistic(request, cname, starttime, endtime);
        return mv;
    }



    @Operation(name="导入excel")
    @RequestMapping("importExcel")
    public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response, Model model,ModelAndView mv) throws IOException, FileUploadException{
        List list = excels(response, request);
        //记录当前行数
        int count = 0;
        List<ExcelHonor> excels = new ArrayList<ExcelHonor>();
        //7为每条记录的字段数
        for (int i = 0; i < list.size(); i = i + 7) {
            count++;
            boolean vaild = judgeDate(list.get(i + 3).toString());
            if (vaild == false) {
                count = count + 2;
                model.addAttribute("msg", "第" + count + "行可能存在错误，请检查后重新导入！");
                mv.setViewName("forward:/honor/selectAll");
                return mv;
            } else {
                String date;
                if (!list.get(i + 3).toString().equals("")) {
                    date = formatDate(list.get(i + 3).toString());
                } else {
                    date = list.get(i + 3).toString();
                }
                ExcelHonor excel = new ExcelHonor(list.get(i).toString(), list.get(i + 1).toString(), list.get(i + 2).toString(), date, list.get(i + 4).toString(), list.get(i + 5).toString(), list.get(i + 6).toString());
                //逐个添加各条数据
                excels.add(excel);
            }
        }
        try {
            int result = honorService.importExcel(excels);
            if (result == count){
                model.addAttribute("msg", "导入成功！");
            }
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            model.addAttribute("msg", "导入失败，请检查输入是否正确或是否存在重复导入！");
        }
        mv.setViewName("forward:/honor/selectAll");
        return mv;
    }

    /**
     * 按主键删除对应数据
     */
    @Operation(name="删除了一条数据")
    @RequestMapping("delete")
    @ResponseBody
    public void delete(String majorkey) {
        honorService.deleteByMajorkey(majorkey);
    }


    /**
     * 按主键修改对应数据
     */
    @Operation(name="修改了一条数据")
    @RequestMapping("alter")
    @ResponseBody
    public void alter(String Hsn, String Hname, String Hwinner, String Hdate, String Hcompany, String department, String Hgrad, String Hremarks) {
        String Haudit = "1";
        Honor honor = new Honor(Hsn, Hname, Hwinner, Hdate, Hcompany, Hgrad, Hremarks, Haudit, department);
        honorService.updateOne(honor);
    }
}