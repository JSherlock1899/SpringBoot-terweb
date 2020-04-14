package com.slxy.terweb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slxy.terweb.controller.base.BaseController;
import com.slxy.terweb.model.Condition;
import com.slxy.terweb.model.Other;
import com.slxy.terweb.model.excel.ExcelOther;
import com.slxy.terweb.service.IOtherService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.slxy.terweb.util.CommonUtils.*;
import static com.slxy.terweb.util.ExcelUtils.excels;
import static com.slxy.terweb.util.ExcelUtils.exportExcelUtil;

/**
 * @program: TeacherWeb
 * @description: 其他成果模块
 * @author: Mr.Jiang
 * @create: 2019-04-21 16:26
 **/

@Operation(name = "其他成果操作")
@Controller
@RequestMapping("other")
public class OtherController extends BaseController<Other> {

    @Autowired
    IOtherService otherService;


    /**
     * 查询当前条件下的其他成果信息
     */
    @Operation(name="查询所有其他成果")
    @RequestMapping("selectAll")
    public ModelAndView findOther(HttpServletRequest request, @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pn,
                                  Map<String,Object> map, String cname, String dname, String starttime, String endtime, String tname){
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn,5);
        //当前条件下的查询结果
        List<Other> others = otherService.selectAll(cname,dname,starttime,endtime,tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(others,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        Condition condition = new Condition(cname,dname,starttime,endtime,tname);
        map.put("pageInfo",pageInfo);
        map.put("condition",condition);
        map.put("type","selectAll");
        mv.addObject("others",others);
        mv.addAllObjects(map);
        mv.setViewName("admin/other/otherList");
        return mv;
    }

    /**
     * 查询未审核的其他成果信息
     */
    @Operation(name="查询未审核的其他成果信息")
    @RequestMapping("audit")
    public ModelAndView findAudit(HttpServletRequest request,@RequestParam(required = false,defaultValue = "1",value = "pn")Integer pn,
                                  Map<String,Object> map, String cname, String dname, String starttime, String endtime, String tname){
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn,5);
        //查询结果
        List<Other> others = otherService.selectAllUnaudit(cname,dname,starttime,endtime,tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(others,5);
        map.put("pageInfo",pageInfo);
        map.put("others",others);
        Condition condition = new Condition(cname,dname,starttime,endtime,tname);
        map.put("condition",condition);
        map.put("type","audit");
        mv.addAllObjects(map);
        mv.setViewName("admin/other/otherAudit");
        return mv;
    }

    /**
     * 跳转到详细信息审核页面
     */
    @Operation(name="跳转到详细信息审核页面")
    @RequestMapping("goDetail")
    public ModelAndView goDetail(HttpServletRequest request,String otherName){
        ModelAndView mv = disposeMVValue(request);
        Other other = otherService.selectByMajorKey(otherName);
        mv.addObject("other",other);
        mv.setViewName("admin/other/detail");
        return mv;
    }

    /**
     * 修改信息，重新提交待审核的荣誉信息
     */
    @Operation(name="修改信息，重新提交待审核的其他成果信息")
    @RequestMapping("updateOne")
    @ResponseBody
    public void updateOne(String otherName,String otherDate, String otherType,String publisher, String otherDescribe){
        String audit = "0";
        Other other = new Other(otherName,otherDate,publisher,audit,otherType,otherDescribe);
        otherService.updateOne(other);
    }

    /**
     * 新建一条其他成果信息
     */
    @Operation(name="新建一条的其他成果信息")
    @RequestMapping(value = "insertOne", method = RequestMethod.POST)
    @ResponseBody
    public void insertOne(HttpServletRequest request, String otherName, String otherDate, String otherType, String publisher, String otherDescribe){
        String audit = "0";
        String Tsn = (String) request.getSession().getAttribute("username");
        Other other = new Other(otherName,otherDate,otherType,publisher,otherDescribe,Tsn,audit,null);
        otherService.insertOne(other);
    }

    /**
     * 导出当前条件下的其他成果信息
     */
    @Operation(name="导出当前条件下的其他成果信息")
    @RequestMapping("export")
    public void export(HttpServletResponse response, String cname, String dname, String starttime, String endtime, String tname){
        ExportExcel<ExcelOther> ex = new ExportExcel<ExcelOther>();
        CommonUtils util = new CommonUtils();
        List<ExcelOther> datalist = otherService.selectExcel(cname.trim(),dname,starttime,endtime,tname);
        String[] headers = {"名称","教师工号" ,"发表人", "类型","日期","发表单位","所属学院","所属专业","学历","职称","成果描述"};
        OutputStream out = null;
        exportExcelUtil(response, ex, util, datalist, headers,"其他成果");
    }

    /**
     * 审核通过
     */
    @Operation(name="审核通过了一条信息")
    @RequestMapping("pass")
    @ResponseBody
    public void pass(String majorkey,String message){
        otherService.pass(majorkey.trim(),message);
    }

    /**
     * 审核不通过
     */
    @Operation(name="审核不通过了一条信息")
    @RequestMapping("nopass")
    @ResponseBody
    public void nopass(String majorkey,String message){
        otherService.nopass(majorkey.trim(),message);
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
    public ModelAndView otherStatistics(HttpServletRequest request, @RequestParam(required = false,defaultValue = "null")String starttime, @RequestParam(required = false,defaultValue = "null")String endtime, @RequestParam(required = false,defaultValue = "null")String cname){
        ModelAndView mv = otherService.statistic(request, cname, starttime, endtime);
        return mv;
    }


    @RequestMapping("importExcel")
    public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response, Model model,ModelAndView mv) throws IOException, FileUploadException{
        List list = excels(response,request);
        //记录当前行数
        int count = 0;
        List<ExcelOther> excelOthers = new ArrayList<ExcelOther>();
        for (int i=0;i<list.size();i=i+6) {
            count++;
            boolean vaild = judgeDate(list.get(i+3).toString());
            if (vaild == false) {
                count = count + 2;
                model.addAttribute("msg", "第" + count + "行可能存在错误，请检查后重新导入！");
                mv.setViewName("forward:/other/selectAll");
                return mv;
            }else {
                    String date;
                    if (!list.get(i+3).toString().equals("")){
                        date = formatDate(list.get(i+3).toString());
                    }else {
                        date = list.get(i+3).toString();
                    }
                    ExcelOther excelOther = new ExcelOther(list.get(i).toString(),list.get(i+1).toString(),list.get(i+2).toString(),date,list.get(i+4).toString(),list.get(i+5).toString());
                    //逐个添加各条数据
                    excelOthers.add(excelOther);
            }
        }
        try {
            int result = otherService.importExcel(excelOthers);
            if (result == count){
                model.addAttribute("msg", "导入成功！");
            }
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            model.addAttribute("msg", "导入失败，请检查成果名是否输入正确或是否存在重复导入！");
        }
        mv.setViewName("forward:/other/selectAll");
        return mv;
    }

    /**
     * 按主键删除对应数据
     */
    @Operation(name="删除了一条数据")
    @RequestMapping("delete")
    @ResponseBody
    public void delete(String majorkey){
        otherService.deleteByMajorkey(majorkey);
    }

    /**
     * 按主键修改对应数据
     */
    @Operation(name="修改了一条数据")
    @RequestMapping("alter")
    @ResponseBody
    public void alter( String otherName,String otherDate, String otherType,String publisher, String otherDescribe){
        String audit = "1";
        Other other = new Other(otherName,otherDate,publisher,audit,otherType,otherDescribe);
        otherService.updateOne(other);
    }
}
