package com.atguigu.yygh.hosp.service.impl;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.hosp.mapper.HospitalSetMapper;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.order.SignInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    //有了这个关系就 extends ServiceImpl<HospitalSetMapper, HospitalSet>不 用注入了



    //测试：查询所有设置信息
    @Autowired
    private  HospitalSetMapper hospitalSetMapper;

    @Autowired
    private HospitalService hospitalService;


    //delete

    @Override
    public String getSignKey(String hoscode) {
        //和传过来的map里面的签名进行校验
        HospitalSet hospitalSet=this.getByHoscode(hoscode);
        if(hospitalSet==null){
            throw new YyghException(ResultCodeEnum.HOSPITAL_OPEN);

        }
        else if (hospitalSet!=null && hospitalSet.getStatus()==0){
            throw new YyghException(ResultCodeEnum.HOSPITAL_LOCK);

        }

        return hospitalSet.getSignKey();

    }

    private HospitalSet getByHoscode(String hoscode) {
        QueryWrapper<HospitalSet> wrapper=new QueryWrapper<>();
        wrapper.eq("hos_code",hoscode);
        HospitalSet hospitalSet=hospitalSetMapper.selectOne(wrapper);
        return hospitalSet;
    }


    //2 根据传递过来医院编码，查询数据库，查询签名
//    @Override
//    public String getSignKey(String hoscode) {
//
//    }
//
//    @Override
//    public SignInfoVo getSignInfoVo(String hoscode) {
//
//    }

}
