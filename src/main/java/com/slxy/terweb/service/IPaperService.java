package com.slxy.terweb.service;


import com.slxy.terweb.model.Paper;
import com.slxy.terweb.model.excel.ExcelPaper;
import com.slxy.terweb.service.base.IBaseService;

import java.util.List;

public interface IPaperService extends IBaseService<Paper> {

    //查询要导出excel的数据
    List<ExcelPaper> selectExcel(String cname, String dname, String starttime, String endtime, String tname);

    //导入excel
    int importExcel(List<ExcelPaper> list);
}
