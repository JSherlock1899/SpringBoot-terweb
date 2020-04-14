package com.slxy.terweb.util;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: TeacherWeb
 * @description: 通用工具类
 * @author: Mr.Jiang
 * @create: 2019-04-27 13:42
 **/

public class CommonUtils {

    public void download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    //将结果集转化成map
    public Map<String,Integer> countRsToMap(ResultSet rs) throws SQLException {
        Map<String,Integer> map = new HashMap<String,Integer>();
        while(rs.next()) {
            map.put(rs.getString("Cname"), rs.getInt("count"));
        }
        return map;
    }

    public Map<String,Integer> collegeCountRsToMap(ResultSet rs) throws SQLException{
        Map<String,Integer> map = new HashMap<String,Integer>();
        while(rs.next()) {
            map.put(rs.getString("Dname"), rs.getInt("count"));
        }
        return map;
    }

    //各院的经费统计
    public Map<String,Integer> moneyRsToMap(ResultSet rs) throws SQLException{
        Map<String,Integer> map = new HashMap<String,Integer>();
        while(rs.next()) {
            map.put(rs.getString("Cname"), rs.getInt("Pmoney"));
        }
        return map;
    }

    //各专业的经费统计
    public Map<String,Integer> countMoneyRsToMap(ResultSet rs) throws SQLException{
        Map<String,Integer> map = new HashMap<String,Integer>();
        while(rs.next()) {
            map.put(rs.getString("Dname"), rs.getInt("Pmoney"));
        }
        return map;
    }

    public static String disposeAuditValue(String audit) {
        if(audit!=null) {
            if(audit.equals("0")) {
                return "未审核";
            }else if(audit.equals("1")) {
                return "审核通过";
            }else if(audit.equals("2")) {
                return "审核未通过";
            }
        }
        return "系统出错！";
    }

    public static String disposeMessageValue(String message) {
        if(message == null) {
            return "无";
        }
        return message;
    }

    //获取一个文件夹下的所有文件
    public File[] getFiles(ArrayList<File> fileList, String path) {
        File[] allFiles = new File(path).listFiles();
        for (int i = 0; i < allFiles.length; i++) {
            File file = allFiles[i];
            if (file.isFile()) {
                fileList.add(file);
            } else  {
                getFiles(fileList, file.getAbsolutePath());
            }
        }
        return allFiles;

    }

    //判断时间格式是否正确
    public static boolean judgeDate(String str){
        if(str.equals(""))
            return true;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            str = formatDate(str);
            if ("false".equals(str)){
                return false;
            }
            Date date = formatter.parse(str);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    //转化时间格式
    public static String formatDate(String s) {
        Date date;
        // 首先设置"Mon Dec 28 00:00:00 CST 2008"的格式,用来将其转化为Date对象
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        //将已有的时间字符串转化为Date对象
        try {
            date = df.parse(s);
        }catch (Exception e){
            return "false";
        }
        // 创建所需的格式
        df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);// 获得格式化后的日期字符串
    }

    /**
     * 判断是否是数字
     */
    public static boolean isNumeric(String str) {
        try {
            boolean b = str.matches("-?[0-9]+\\.?[0-9]*");
            return b;
        } catch(NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 处理需要的公共值
     * @param request
     * @return
     */
    public static ModelAndView disposeMVValue(HttpServletRequest request) {
        List<String> collegeList = (List<String>) request.getSession().getAttribute("collegeList");
        Boolean school = (Boolean) request.getSession().getAttribute("school");
        Boolean academy = (Boolean) request.getSession().getAttribute("academy");
        String name = (String) request.getSession().getAttribute("name");
        String cname = (String) request.getSession().getAttribute("cname");
        String grade = (String) request.getSession().getAttribute("grade");
        Map<String, Object> map = new HashMap<>();
        map.put("collegeList",collegeList);
        map.put("school",school);
        map.put("academy",academy);
        map.put("name",name);
        map.put("cname",cname);
        map.put("grade",grade);
        ModelAndView mv = new ModelAndView();
        mv.addAllObjects(map);
        return mv;
    }

    /**
     * 计算总数
     * @param list
     * @return
     */
    public static Integer calcAmount(List<Map<String, Integer>> list) {
        Integer amount = 0;
        Map<String, Integer> map = list.get(0);
        for (Map.Entry<String, Integer> m : map.entrySet()) {
            amount = amount + m.getValue();
        }
        return amount;
    }

    /**
     * 处理经费为0的学院数据
     * @param maps
     * @return
     */
    public static List<Map<String, Integer>> disposeMoneyMap(List<Map<String, Integer>> maps) {
        for (Map<String, Integer> map : maps) {
            if (!map.containsKey("money")){
                map.put("money", 0);
            }
        }
        return maps;
    }

    /**
     * 处理折线图的表格数据
     * @param lists
     * @return
     */
    public static List<List<Integer>> disposeLineData(List<List<Map<String, Integer>>> lists) {
        List<List<Integer>> newData = new ArrayList<>();
        List<Map<String, Integer>> newList = new ArrayList<>();
        List<Integer> newList1 = new ArrayList<>();
        List<Integer> newList2 = new ArrayList<>();
        List<Integer> newList3 = new ArrayList<>();
        List<Integer> newList4 = new ArrayList<>();
        List<Integer> newList5 = new ArrayList<>();
        List<Integer> newList6 = new ArrayList<>();
        List<Integer> newList7 = new ArrayList<>();
        List<Integer> newList8 = new ArrayList<>();
        List<Integer> newList9 = new ArrayList<>();
        List<Integer> newList10 = new ArrayList<>();
        newList.addAll(lists.get(0));
        newList.addAll(lists.get(1));
        newList.addAll(lists.get(2));
        newList.addAll(lists.get(3));
        newList.addAll(lists.get(4));
        for (int i = 0; i < 50; i = i + 5) {
            newList1.add(newList.get(i).values().iterator().next());
            newList2.add(newList.get(i+1).values().iterator().next());
            newList3.add(newList.get(i+2).values().iterator().next());
            newList4.add(newList.get(i+3).values().iterator().next());
            newList5.add(newList.get(i+4).values().iterator().next());
            newList6.add(newList.get(i+5).values().iterator().next());
            newList7.add(newList.get(i+6).values().iterator().next());
            newList8.add(newList.get(i+7).values().iterator().next());
            newList9.add(newList.get(i+8).values().iterator().next());
            newList10.add(newList.get(i+9).values().iterator().next());
        }
        newData.addAll(Collections.singleton(newList1));
        newData.addAll(Collections.singleton(newList2));
        newData.addAll(Collections.singleton(newList3));
        newData.addAll(Collections.singleton(newList4));
        newData.addAll(Collections.singleton(newList5));
        newData.addAll(Collections.singleton(newList6));
        newData.addAll(Collections.singleton(newList7));
        newData.addAll(Collections.singleton(newList8));
        newData.addAll(Collections.singleton(newList9));
        newData.addAll(Collections.singleton(newList10));
        System.out.println(newData.toString());
        return newData;
    }
}
