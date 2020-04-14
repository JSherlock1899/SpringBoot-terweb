package com.slxy.terweb.controller;


import com.slxy.terweb.util.CommonUtils;
import com.slxy.terweb.util.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: TeacherWeb
 * @description: excel控制类
 * @author: Mr.Jiang
 * @create: 2019-04-27 14:07
 **/

@Operation(name = "excel操作")
@Controller
@RequestMapping("poi")
public class PoiController {

    @Operation(name = "下载模板")
    @RequestMapping("getTemplate")
    public void getTemplate(HttpServletResponse response, String name){
//        CreateTemplate createTemplate = new CreateTemplate();
//        createTemplate.createExcel(response,name);
        CommonUtils commonUtils = new CommonUtils();
        String filepath = "/java/terweb/resources/" + name + ".xls";
        System.out.println(filepath);
        commonUtils.download(filepath, response);
    }
}
