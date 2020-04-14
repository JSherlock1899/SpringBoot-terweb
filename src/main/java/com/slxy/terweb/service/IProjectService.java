package com.slxy.terweb.service;

import com.slxy.terweb.model.Project;
import com.slxy.terweb.model.excel.ExcelProject;
import com.slxy.terweb.service.base.IBaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IProjectService extends IBaseService<Project> {

    //查询要导出excel的数据
    List<ExcelProject> selectExcel(String cname, String dname, String starttime, String endtime, String tname);

    //导入excel
    int importExcel(List<ExcelProject> list);

    //获取各学院项目经费
    List<Map<String, Integer>> getProjectMoney(String starttime, String endtime);

    //获取各专业项目经费
    List<Map<String, Integer>> getSdeptProjectMoney(String starttime, String endtime, String Cname);

    //获取总经费
    BigDecimal selectProjectMoney(String cname, String starttime, String endtime);
}
