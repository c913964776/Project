package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface HospitalService {
    //上传医院接口
    void save(Map<String, Object> paramMap);

    //实现根据医院编号查询


    //医院列表(条件查询分页)

    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);



    //更新医院上线状态

    //医院详情信息


    //获取医院名称


    //根据医院名称查询

    //根据医院编号获取医院预约挂号详情

}
