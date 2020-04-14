package com.slxy.terweb.service;


import com.slxy.terweb.model.Honor;
import com.slxy.terweb.model.excel.ExcelHonor;
import com.slxy.terweb.service.base.IBaseService;

import java.util.List;

public interface IHonorService  extends IBaseService<Honor> {

    //查询要导出excel的数据
    List<ExcelHonor> selectExcel(String cname, String dname, String starttime, String endtime, String tname);

    //导入excel
    int importExcel(List<ExcelHonor> list);
}
