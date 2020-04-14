package com.slxy.terweb.service.base;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IBaseService<T> {

    //查询所有数据
    List<T> selectAll(String cname, String dname, String starttime, String endtime, String tname);

    //查询未审核的数据
    List<T> selectAllUnaudit(String cname, String dname, String starttime, String endtime, String tname);

    T selectByMajorKey(String majorkey);

    //更新数据
    int updateOne(T t);

    //插入数据
    int insertOne(T t);

    //审核通过
    void pass(String majorkey, String messsage);

    //审核不通过
    void nopass(String majorkey, String messsage);

    //保存附件路径
    void savePath(String majorkey, String path);

    //获取附件路径
    String getPath(String majorkey);

    //获取各学院项目数目
    List<Map<String, Integer>> getCollegeCount(String starttime, String endtime);

    //获取各专业项目数目
    List<Map<String, Integer>> getSdeptCount(String starttime, String endtime, String Cname);

    //获取近5年各学院项目数量
    List<Map<String, Integer>> getRecentYearsCount(String starttime, String endtime);

    //获取近5年各专业项目数量
    List<Map<String, Integer>> getRecentYearsSdeptCount(String starttime, String endtime, String cname);

    //按主键删除信息
    int deleteByMajorkey(String majorkey);

    //管理员修改信息
    void alterByMajorkey(T t);

    //获取详细信息
    ModelAndView goDetail(HttpServletRequest request, T t,ModelAndView mv);

    //绘制统计图
    ModelAndView statistic(HttpServletRequest request,String starttime, String endtime,String cname);

    //查询总数
    Integer selectCount(String cname, String starttime, String endtime);

    //导入excel
//    List<T> importExcel(Model model,List list);
}
