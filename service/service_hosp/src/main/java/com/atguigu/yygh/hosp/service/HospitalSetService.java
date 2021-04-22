package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.order.SignInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashSet;
import java.util.List;

public interface HospitalSetService extends IService<HospitalSet> {

    //测试，查询医院设置表中的所有信息，不用谢service层，因为service层继承了Iservice，里面包含了list方法

    //delete


    //



    //2 根据传递过来医院编码，查询数据库，查询签名
    String getSignKey(String hoscode);



    //获取医院签名信息

}
