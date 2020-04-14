package com.slxy.terweb.mapper;

import com.slxy.terweb.model.Paper;
import com.slxy.terweb.model.excel.ExcelPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaperMapper extends BaseMapper<Paper>{

    //查询要导出excel的数据
    List<ExcelPaper> selectExcel(@Param("cname") String cname, @Param("dname") String dname, @Param("starttime") String starttime, @Param("endtime") String endtime, @Param("tname") String tname);

    //导入excel
    int importExcel(List<ExcelPaper> list);
}


