package com.atguigu.yygh.user.mapper;

import com.atguigu.yygh.model.user.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PatientMapper extends BaseMapper<Patient> {
    void selectList();


    @Select("select * from patients")
    List<Patient> selectAll();
}
