package com.slxy.terweb.service;

import java.util.ArrayList;
import java.util.List;

public interface ICollegeService {

    //查询所有的学院信息
    List<String> selectAllCollegeName();

    //将学院号转化为学院名
    String getCname(String Csn);

    //根据学院名查询有该学院哪些专业
    ArrayList<String> selectSdeptByCname(String cname);

}
