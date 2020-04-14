package com.slxy.terweb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slxy.terweb.controller.base.BaseController;
import com.slxy.terweb.model.Condition;
import com.slxy.terweb.model.Patent;
import com.slxy.terweb.model.excel.ExcelPatent;
import com.slxy.terweb.service.IPatentService;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.slxy.terweb.util.CommonUtils.*;
import static com.slxy.terweb.util.ExcelUtils.excels;
import static com.slxy.terweb.util.ExcelUtils.exportExcelUtil;
/**
 * @program: TeacherWeb
 * @description:专利模块
 * @author: Mr.Jiang
 * @create: 2019-04-19 22:14
 **/

@Operation(name = "专利操作")
@Controller
@RequestMapping("patent")
public class PatentController extends BaseController<Patent> {

    @Autowired
    IPatentService patentService;


    /**
     * 查询当前条件下的专利信息
     */
    @Operation(name="查询所有专利")
    @RequestMapping("selectAll")
    public ModelAndView findPatent(HttpServletRequest request,@RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pn,
                                   Map<String, Object> map, String cname, String dname, String starttime, String endtime, String tname) {
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn, 5);
        //当前条件下的查询结果
        List<Patent> patents = patentService.selectAll(cname, dname, starttime, endtime, tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(patents, 5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        Condition condition = new Condition(cname, dname, starttime, endtime, tname);
        map.put("pageInfo",pageInfo);
        map.put("condition",condition);
        map.put("type","selectAll");
        map.put("patents", patents);
        mv.addAllObjects(map);
        mv.setViewName("admin/patent/patentList");
        return mv;
    }


    /**
     * 导出当前条件下的专利信息
     */
    @Operation(name="导出当前条件下的专利信息")
    @RequestMapping("export")
    public void exportPatent(HttpServletResponse response, String cname, String dname, String starttime, String endtime, String tname) {
        ExportExcel<ExcelPatent> ex = new ExportExcel<ExcelPatent>();
        CommonUtils util = new CommonUtils();
        List<ExcelPatent> datalist = patentService.selectExcel(cname.trim(), dname, starttime, endtime, tname);
        String[] headers = {"名称", "授权号", "申请时间", "授权时间", "类型", "发明人","教师工号", "专利权人", "所属学院", "所属专业", "学历", "职称", "备注"};
        exportExcelUtil(response, ex, util, datalist, headers, "专利");
    }


    /**
     * 查询未审核的专利信息
     */
    @Operation(name="查询未审核的专利信息")
    @RequestMapping("audit")
    public ModelAndView findAudit(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                  Map<String, Object> map, String cname, String dname, String starttime, String endtime, String tname) {
        ModelAndView mv = disposeMVValue(request);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn, 5);
        //当前条件下的查询结果
        List<Patent> patents = patentService.selectAllUnaudit(cname, dname, starttime, endtime, tname);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(patents, 5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        map.put("pageInfo", pageInfo);
        mv.addObject("patents", patents);
        Condition condition = new Condition(cname, dname, starttime, endtime, tname);
        mv.addObject("condition", condition);
        mv.addObject("type", "audit");
        mv.addAllObjects(map);
        mv.setViewName("admin/patent/patentAudit");
        return mv;
    }

    /**
     * 跳转到详细信息审核页面
     */
    @Operation(name="跳转到详细信息审核页面")
    @RequestMapping("goDetail")
    public ModelAndView goDetail(HttpServletRequest request,String patsn) {
        ModelAndView mv = disposeMVValue(request);
        Patent patent = patentService.selectByMajorKey(patsn);
        mv.addObject("patent",patent);
        mv.setViewName("admin/patent/detail");
        return mv;
    }

    /**
     * 修改信息，重新提交待审核的专利信息
     */
    @Operation(name="修改信息，重新提交待审核的专利信息")
    @RequestMapping("updateOne")
    @ResponseBody
    public void updateOne(HttpServletRequest request, String Patname, String inventor, String Patsn, String Patapdate, String Patendate, String Patkind, String Patremarks) {
        String Paudit = "0";
        String Tsn = (String) request.getSession().getAttribute("username");
        Patent patent = new Patent(Patname, Patsn, Patapdate, Patendate, Patkind, Patremarks, inventor, Tsn, Paudit);
        int result = patentService.updateOne(patent);
    }

    /**
     * 新建一条的专利信息
     */
    @Operation(name="新建一条的专利信息")
    @RequestMapping(value = "insertOne", method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public void insertOne(HttpServletRequest request, String Patname, String inventor, String Patsn, String Patapdate, String Patendate, String Patkind, String Patremarks) {
        String Paudit = "0";
        String Tsn = (String) request.getSession().getAttribute("username");
        Patent patent = new Patent(Patname, Patsn, Patapdate, Patendate, Patkind, Patremarks, inventor, Tsn, Paudit);
        int result = patentService.insertOne(patent);
//        if(result == 1){
//            return JsonMsg.success();
//        }else {
//            return JsonMsg.fail().addInfo("update_error","新建数据出错，请检查授权号是否输入正确！");
//        }
    }

    /**
     * 审核通过
     */
    @Operation(name="审核通过了一条信息")
    @RequestMapping("pass")
    @ResponseBody
    public void pass(String majorkey, String message) {
        patentService.pass(majorkey, message);
    }

    /**
     * 审核不通过
     */
    @Operation(name="审核不通过了一条信息")
    @RequestMapping("nopass")
    @ResponseBody
    public void nopass(String majorkey, String message) {
        patentService.nopass(majorkey, message);
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
    public ModelAndView patentStatistics(HttpServletRequest request, @RequestParam(required = false, defaultValue = "null") String starttime, @RequestParam(required = false, defaultValue = "null") String endtime, @RequestParam(required = false, defaultValue = "null") String cname) {
        ModelAndView mv = patentService.statistic(request, cname, starttime, endtime);
        return mv;
    }



    /**
     * 导入excel
     * @param request
     * @param response
     * @throws IOException
     * @throws FileUploadException
     * @throws ParseException
     */
    @Operation(name="导入excel")
    @RequestMapping("importExcel")
    public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response, Model model,ModelAndView mv) throws IOException, FileUploadException {
        List list = excels(response,request);
        //记录当前行数
        int count = 0;
        List<ExcelPatent> excels = new ArrayList<>();
        //8为每条记录的字段数
        for (int i=0;i<list.size();i=i+8) {
            count++;
            boolean vaild1 = judgeDate(list.get(i+4).toString());
            boolean vaild2 = judgeDate(list.get(i+5).toString());
            if (vaild1 == false || vaild2 == false) {
                count = count + 2;
                model.addAttribute("msg", "第" + count + "行可能存在错误，请检查后重新导入！");
                mv.setViewName("forward:/patent/selectAll");
                return mv;
            }else {
                //分别为立项时间和结题时间
                String date1;
                String date2;
                if (!list.get(i+4).toString().equals("")){
                    date1 = formatDate(list.get(i+4).toString());
                }else {
                    date1 = list.get(i+4).toString();
                }
                if (!list.get(i+5).toString().equals("")){
                    date2 = formatDate(list.get(i+5).toString());
                }else {
                    date2 = list.get(i+5).toString();
                }
                try {
                    ExcelPatent excel = new ExcelPatent(list.get(i).toString(),list.get(i+1).toString(),list.get(i+2).toString(),list.get(i+3).toString(),date1,date2,list.get(i+6).toString(),list.get(i+7).toString());
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
            int result = patentService.importExcel(excels);
            if (result == count){
                model.addAttribute("msg", "导入成功！");
            }
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            model.addAttribute("msg", "导入失败，请检查专利号输入是否正确或是否存在重复导入！");
        }
        mv.setViewName("forward:/patent/selectAll");
        return mv;
    }

    /**
     * 按主键删除对应数据
     */
    @Operation(name="删除了一条数据")
    @RequestMapping("delete")
    @ResponseBody
    public void delete(String majorkey){
        patentService.deleteByMajorkey(majorkey);
    }

    /**
     * 按主键修改对应数据
     */
    @Operation(name="修改了一条数据")
    @RequestMapping("alter")
    @ResponseBody
    public void alter(String Patname, String inventor, String Patsn, String Patapdate, String Patendate, String Patkind) {
        String Paudit = "1";
        Patent patent = new Patent(Patname, Patsn, Patapdate, Patendate, Patkind, inventor, Paudit);
        patentService.alterByMajorkey(patent);
    }
}


