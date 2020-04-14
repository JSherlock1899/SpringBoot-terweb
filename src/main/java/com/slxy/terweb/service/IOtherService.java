package com.slxy.terweb.service;


import com.slxy.terweb.model.Other;
import com.slxy.terweb.model.excel.ExcelOther;
import com.slxy.terweb.service.base.IBaseService;

import java.util.List;

public interface IOtherService extends IBaseService<Other> {

    //查询要导出excel的数据
    List<ExcelOther> selectExcel(String cname, String dname, String starttime, String endtime, String tname);

    //导入excel
    int importExcel(List<ExcelOther> list);

}
