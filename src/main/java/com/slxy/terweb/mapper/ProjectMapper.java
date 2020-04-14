package com.slxy.terweb.mapper;

import com.slxy.terweb.model.Project;
import com.slxy.terweb.model.excel.ExcelProject;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProjectMapper extends BaseMapper<Project>{

    //查询要导出excel的数据
    List<ExcelProject> selectExcel(@Param("cname") String cname, @Param("dname") String dname, @Param("starttime") String starttime, @Param("endtime") String endtime, @Param("tname") String tname);

    //导入excel
    int importExcel(List<ExcelProject> list);

    //获取各学院项目经费
    List<Map<String, Integer>> getProjectMoney(@Param("starttime") String starttime, @Param("endtime") String endtime);

    //获取各专业项目经费
    List<Map<String, Integer>> getSdeptProjectMoney(@Param("starttime") String starttime, @Param("endtime") String endtime, @Param("cname") String Cname);

    //获取总经费
    BigDecimal selectProjectMoney(String cname, String starttime, String endtime);
}
