package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.cmn.client.DictFeignClient;
import com.atguigu.yygh.hosp.repository.HospitalRepository;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.REUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> paramMap){
//        JSON.parseObject（String str）是将str转化为相应的JSONObject对象，
//        其中str是“键值对”形式的json字符串，
//        转化为JSONObject对象之后就可以使用其内置的方法，进行各种处理了。
        //把传入的map变为一个对象hospital
        Hospital hospital=JSONObject.parseObject(JSONObject.toJSONString(paramMap),Hospital.class);
        //根据hoscode在manggodb数据库中得到对应的医院
        Hospital targethospital =hospitalRepository.getHospitalByHoscode(hospital.getHoscode());
        if(targethospital!=null){
            hospital.setStatus(targethospital.getStatus());
            hospital.setCreateTime(targethospital.getCreateTime());
            hospital.setUpdateTime(new Date());
            //0表示还没有上线
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);

        }

        else {
            hospital.setUpdateTime(new Date());
            hospital.setCreateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }


    }






    @Override
    public Hospital getByHoscode(String hoscode) {
        hospitalRepository.getHospitalByHoscode(hoscode);

    }

    //医院列表(条件查询分页)
    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {


//        Example api的组成
//        Probe: 含有对应字段的实例对象。
//        ExampleMatcher：ExampleMatcher携带有关如何匹配特定字段的详细信息，相当于匹配条件。
//        Example：由Probe和ExampleMatcher组成，用于查询。


        //创建排序对象 sort  安装creattime降序排序
        Sort sort=Sort.by(Sort.Direction.DESC,"creatTime");

        //0.创建pageable对象
        Pageable pageable=PageRequest.of(page-1,limit,sort);

        //1.将vo中的信息拷贝到hospital中
        Hospital hospital=new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo,hospital);

        //2.创建matcher匹配器
        ExampleMatcher matcher=ExampleMatcher.matching().withMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);

        //3.创建实例
        Example<Hospital> example=Example.of(hospital,matcher);

        Page<Hospital> pages=hospitalRepository.findAll(example,pageable);
        return pages;nm



    }

    //更新医院上线状态
    @Override
    public void updateStatus(String id, Integer status) {

    }

    @Override
    public Map<String, Object> getHospById(String id) {

    }

    //获取医院名称
    @Override
    public String getHospName(String hoscode) {

    }

    //根据医院名称查询
    @Override
    public List<Hospital> findByHosname(String hosname) {

    }

    //根据医院编号获取医院预约挂号详情
    @Override
    public Map<String, Object> item(String hoscode) {

    }

    //获取查询list集合，遍历进行医院等级封装
    private Hospital setHospitalHosType(Hospital hospital) {

    }
}
