package com.slxy.terweb.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.slxy.terweb.mapper.PaperMapper;
import com.slxy.terweb.model.Condition;
import com.slxy.terweb.model.Paper;
import com.slxy.terweb.model.excel.ExcelPaper;
import com.slxy.terweb.service.ICollegeService;
import com.slxy.terweb.service.IPaperService;
import com.slxy.terweb.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.slxy.terweb.controller.base.BaseController.yearsArr;
import static com.slxy.terweb.util.CommonUtils.*;


@Service("PaperService")
@Transactional
public class PaperServiceImpl extends BaseServiceImpl<Paper> implements IPaperService {

    @Autowired
    PaperMapper paperMapper;
    @Autowired
    ICollegeService collegeService;

    @Override
    public List<Paper> selectAll(String cname, String dname, String starttime, String endtime, String tname) {
        return paperMapper.selectAll(cname,dname,starttime,endtime,tname);
    }

    @Override
    public List<Paper> selectAllUnaudit(String cname, String dname, String starttime, String endtime, String tname) {
        return paperMapper.selectAllUnaudit(cname,dname,starttime,endtime,tname);
    }

    @Override
    public Paper selectByMajorKey(String majorkey) {
        return paperMapper.selectByMajorKey(majorkey);
    }

    @Override
    public int updateOne(Paper paper) {
        return  paperMapper.updateOne(paper);
    }

    @Override
    public int insertOne(Paper paper) {
        return paperMapper.insertOne(paper);
    }

    @Override
    public List<ExcelPaper> selectExcel(String cname, String dname, String starttime, String endtime, String tname) {
        return paperMapper.selectExcel(cname,dname,starttime,endtime,tname);
    }

    @Override
    public int importExcel(List<ExcelPaper> list) {
        return paperMapper.importExcel(list);
    }

    @Override
    public void pass(String majorkey, String messsage) {
        paperMapper.pass(majorkey,messsage);
    }

    @Override
    public void nopass(String majorkey, String messsage) {
        paperMapper.nopass(majorkey,messsage);
    }

    @Override
    public void savePath(String majorkey, String path) {
        paperMapper.savePath(majorkey,path);
    }

    @Override
    public String getPath(String majorkey) {
        return paperMapper.getPath(majorkey);
    }

    @Override
    public List<Map<String, Integer>> getCollegeCount(String starttime,String endtime) {
        return paperMapper.getCollegeCount(starttime, endtime);
    }

    @Override
    public List<Map<String, Integer>> getSdeptCount(String starttime, String endtime,String Cname) {
        return paperMapper.getSdeptCount(starttime,endtime,Cname);
    }

    @Override
    public List<Map<String, Integer>> getRecentYearsCount(String starttime, String endtime) {
        return paperMapper.getRecentYearsCount(starttime,endtime);
    }

    @Override
    public List<Map<String, Integer>> getRecentYearsSdeptCount(String starttime, String endtime, String cname) {
        return paperMapper.getRecentYearsSdeptCount(starttime,endtime,cname);
    }

    @Override
    public int deleteByMajorkey(String majorkey) {
        return paperMapper.deleteByMajorkey(majorkey);
    }

    @Override
    public void alterByMajorkey(Paper paper) {
        paperMapper.alterByMajorkey(paper);
    }

    @Override
    public ModelAndView goDetail(HttpServletRequest request, Paper paper,ModelAndView mv) {
        String audit = paper.getPaudit();
        //处理审核字段
        String auditString = disposeAuditValue(audit);
        String message = paper.getMessage();
        String messageString = disposeMessageValue(message);
        //覆盖原审核情况和审核意见
        paper.setMessage(messageString);
        paper.setPaudit(auditString);
        mv.addObject("paper",paper);
        mv.setViewName("teacher/paper/detail");
        return mv;
    }


    @Override
    public ModelAndView statistic(HttpServletRequest request, String cname, String starttime, String endtime) {
        ModelAndView mv = disposeMVValue(request);
        //柱状图和饼图数据源
        List<Map<String, Integer>> map = new ArrayList<Map<String, Integer>>();
        //折线图数据源
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        List<List<Map<String, Integer>>> lists = new ArrayList<List<Map<String, Integer>>>();
        String grade = (String) request.getSession().getAttribute("grade");
        //校级管理员
        if(grade.equals("1")){
            if (cname == null || cname.equals("null")){
                map = getCollegeCount(starttime,endtime);
                //折线图数据
                for (int i=0;i<yearsArr.length-1;i++){
                    mapList = getRecentYearsCount(yearsArr[i],yearsArr[i+1]);
                    lists.add(mapList);
                }
            }else {
                //校管理员查询二级学院下各专业的数据
                map = getSdeptCount(starttime,endtime,cname);
                getCount(cname, lists);
            }

        }else if (grade.equals("2")){
            //院级管理员
            cname = (String) request.getSession().getAttribute("cname");
            map = getSdeptCount(starttime,endtime,cname);
            getCount(cname, lists);
        }
        //总数
        Integer amount = selectCount(cname,starttime,endtime);
        mv.addObject("amount",amount);
        //当查询的是学院下各专业的情况时查询该学院有哪些专业
        if (!"null".equals(cname) && cname != null) {
            List<String> dnameList = collegeService.selectSdeptByCname(cname);
            mv.addObject("dnameList",dnameList);
        }
        //将map转化为json，以传给前端
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(map));
        JSONArray jsonList = JSONArray.parseArray(JSON.toJSONString(lists));
        mv.addObject("json",json);
        mv.addObject("jsonList",jsonList);
        mv.addObject("yearsArr",yearsArr);
        mv.addObject("cname",cname);
        Condition condition = new Condition(cname, starttime, endtime);
        mv.addObject("condition",condition);
        mv.addObject("type","statistics");
        mv.setViewName("admin/paper/statistics");
        return mv;
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
    public Integer selectCount(String cname, String starttime, String endtime) {
        return paperMapper.selectCount(cname,starttime,endtime);
    }

}
