package com.slxy.terweb.service.impl;

import com.slxy.terweb.mapper.CollegeMapper;
import com.slxy.terweb.service.ICollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: TeacherWeb
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-04-18 15:48
 **/

@Service("CollegeService")
@Transactional
public class CollegeServiceImpl implements ICollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public List<String> selectAllCollegeName() {
        return collegeMapper.selectAllCollegeName();
    }

    @Override
    public String getCname(String Csn) {
        return collegeMapper.getCname(Csn);
    }

    @Override
    public ArrayList<String> selectSdeptByCname(String cname) {
        return collegeMapper.selectSdeptByCname(cname);
    }


}
