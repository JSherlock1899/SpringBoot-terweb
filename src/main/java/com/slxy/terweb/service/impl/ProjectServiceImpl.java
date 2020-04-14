package com.slxy.terweb.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.slxy.terweb.mapper.ProjectMapper;
import com.slxy.terweb.model.Condition;
import com.slxy.terweb.model.Project;
import com.slxy.terweb.model.excel.ExcelProject;
import com.slxy.terweb.service.ICollegeService;
import com.slxy.terweb.service.IProjectService;
import com.slxy.terweb.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.slxy.terweb.controller.base.BaseController.yearsArr;
import static com.slxy.terweb.util.CommonUtils.*;

/**
 * @program: TeacherWeb
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-04-21 16:41
 **/

@Service("ProjectService")
@Transactional
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements IProjectService {

    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ICollegeService collegeService;

    @Override
    public List<Project> selectAll(String cname, String dname, String starttime, String endtime, String tname) {
        return projectMapper.selectAll(cname,dname,starttime,endtime,tname);

    }

    @Override
    public List<Project> selectAllUnaudit(String cname, String dname, String starttime, String endtime, String tname) {
        return projectMapper.selectAllUnaudit(cname,dname,starttime,endtime,tname);
    }

    @Override
    public Project selectByMajorKey(String majorkey) {
        return projectMapper.selectByMajorKey(majorkey);
    }

    @Override
    public int updateOne(Project project) {
        return projectMapper.updateOne(project);
    }

    @Override
    public int insertOne(Project project) {
        return projectMapper.insertOne(project);
    }

    @Override
    public void pass(String majorkey, String messsage) {
        projectMapper.pass(majorkey,messsage);
    }

    @Override
    public void nopass(String majorkey, String messsage) {
        projectMapper.nopass(majorkey,messsage);
    }

    @Override
    public void savePath(String majorkey, String path) {
        projectMapper.savePath(majorkey,path);
    }

    @Override
    public String getPath(String majorkey) {
        return projectMapper.getPath(majorkey);
    }

    @Override
    public List<Map<String, Integer>> getCollegeCount(String starttime,String endtime) {
        return projectMapper.getCollegeCount(starttime, endtime);
    }

    @Override
    public List<Map<String, Integer>> getSdeptCount(String starttime, String endtime,String Cname) {
        return projectMapper.getSdeptCount(starttime,endtime,Cname);
    }

    @Override
    public List<Map<String, Integer>> getRecentYearsCount(String starttime, String endtime) {
        return projectMapper.getRecentYearsCount(starttime,endtime);
    }

    @Override
    public List<Map<String, Integer>> getRecentYearsSdeptCount(String starttime, String endtime, String cname) {
        return projectMapper.getRecentYearsSdeptCount(starttime,endtime,cname);
    }

    @Override
    public int deleteByMajorkey(String majorkey) {
        return projectMapper.deleteByMajorkey(majorkey);
    }

    @Override
    public void alterByMajorkey(Project project) {
        projectMapper.alterByMajorkey(project);
    }

    @Override
    public ModelAndView goDetail(HttpServletRequest request,Project project,ModelAndView mv) {
        String audit = project.getPaudit();
        //处理审核字段
        String auditString = disposeAuditValue(audit);
        String message = project.getMessage();
        String messageString = disposeMessageValue(message);
        //覆盖原审核情况和审核意见
        project.setMessage(messageString);
        project.setPaudit(auditString);
        mv.addObject("project",project);
        mv.setViewName("teacher/project/detail");
        return mv;
    }

    @Override
    public ModelAndView statistic(HttpServletRequest request, String cname, String starttime, String endtime) {
        ModelAndView mv = disposeMVValue(request);
        //柱状图和饼图数据源
        List<Map<String, Integer>> map = new ArrayList<>();
        List<Map<String, Integer>> moneyMap = new ArrayList<>();
        //折线图数据源
        List<Map<String, Integer>> mapList;
        List<List<Map<String, Integer>>> lists = new ArrayList<List<Map<String, Integer>>>();
        String grade = (String) request.getSession().getAttribute("grade");
        if(grade.equals("1")){
            if (cname == null || cname.equals("null")){
                map = getCollegeCount(starttime,endtime);
                moneyMap = getProjectMoney(starttime,endtime);
                //折线图数据
                for (int i=0;i<yearsArr.length-1;i++){
                    mapList = getRecentYearsCount(yearsArr[i],yearsArr[i+1]);
                    lists.add(mapList);
                }
            }else {
                //校管理员查询二级学院下各专业的数据
                map = getSdeptCount(starttime,endtime,cname);
                moneyMap = getSdeptProjectMoney(starttime,endtime,cname);
                //处理经费为0的二级学院下各专业的数据
                moneyMap =disposeMoneyMap(moneyMap);
                //处理折线图数据
                getCount(cname, lists);
            }
        }else if (grade.equals("2")){
            cname = (String) request.getSession().getAttribute("cname");
            map = getSdeptCount(starttime,endtime,cname);
            moneyMap = getSdeptProjectMoney(starttime,endtime,cname);
            getCount(cname, lists);
        }//处理经费为0的学院数据
        moneyMap =disposeMoneyMap(moneyMap);
        //项目总数
        Integer amount = selectCount(cname,starttime,endtime);
        mv.addObject("amount",amount);
        //经费总数
        BigDecimal moneyAmount = selectProjectMoney(cname,starttime,endtime);
        mv.addObject("moneyAmount",moneyAmount);
        //当查询的是学院下各专业的情况时查询该学院有哪些专业
        if (!"null".equals(cname) && cname != null) {
            List<String> dnameList = collegeService.selectSdeptByCname(cname);
            mv.addObject("dnameList",dnameList);
        }
        //将map转化为json，以传给前端
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(map));
        JSONArray jsonList = JSONArray.parseArray(JSON.toJSONString(lists));
        JSONArray jsonMoney = JSONArray.parseArray(JSON.toJSONString(moneyMap));
        System.out.println(lists.toString());
        mv.addObject("json",json);
        mv.addObject("jsonList",jsonList);
        mv.addObject("jsonMoney",jsonMoney);
        mv.addObject("yearsArr",yearsArr);
        mv.addObject("cname",cname);
        Condition condition = new Condition(cname, starttime, endtime);
        mv.addObject("condition",condition);
        mv.addObject("type","statistics");
        mv.setViewName("admin/project/statistics");
        return mv;
    }

    @Override
    public Integer selectCount(String cname, String starttime, String endtime) {
        return projectMapper.selectCount(cname,starttime,endtime);
    }



    private void getCount(@RequestParam(required = false, defaultValue = "null") String cname, List<List<Map<String, Integer>>> lists) {
        String starttime;
        String endtime;
        List<Map<String, Integer>> mapList;
        for (int i = 0; i<yearsArr.length-1; i++){
            starttime = yearsArr[i];
            endtime = yearsArr[i+1];
            mapList = getRecentYearsSdeptCount(starttime,endtime,cname);
            lists.add(mapList);
        }
    }

    @Override
    public List<ExcelProject> selectExcel(String cname, String dname, String starttime, String endtime, String tname) {
        return projectMapper.selectExcel(cname,dname,starttime,endtime,tname);
    }

    @Override
    public int importExcel(List<ExcelProject> list) {
        return projectMapper.importExcel(list);
    }

    @Override
    public List<Map<String, Integer>> getProjectMoney(String starttime, String endtime) {
        return projectMapper.getProjectMoney(starttime,endtime);
    }

    @Override
    public List<Map<String, Integer>> getSdeptProjectMoney(String starttime, String endtime, String Cname) {
        return projectMapper.getSdeptProjectMoney(starttime,endtime,Cname);
    }

    @Override
    public BigDecimal selectProjectMoney(String cname, String starttime, String endtime) {
        return projectMapper.selectProjectMoney(cname, starttime, endtime);
    }

}
