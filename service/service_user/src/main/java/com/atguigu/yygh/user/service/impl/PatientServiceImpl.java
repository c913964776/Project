package com.atguigu.yygh.user.service.impl;

import com.atguigu.yygh.cmn.client.DictFeignClient;
import com.atguigu.yygh.enums.DictEnum;
import com.atguigu.yygh.model.user.Patient;
import com.atguigu.yygh.model.user.UserInfo;
import com.atguigu.yygh.user.mapper.PatientMapper;
import com.atguigu.yygh.user.mapper.UserInfoMapper;
import com.atguigu.yygh.user.service.PatientService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl extends
        ServiceImpl<PatientMapper, Patient> implements PatientService {

    @Autowired
    private DictFeignClient dictFeignClient;

    @Autowired
    private PatientMapper patientMapper;












    //获取就诊人列表
    @Override
    public List<Patient> findAll(Long userId) {
        //先根据id获得id下的所有病人
        QueryWrapper<Patient> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Patient> patients = patientMapper.selectList(wrapper);

        //信息并不完整，里面的地区啥的都是编号而不是具体的值 所以要用到cmn服务里面的字典
        for(Patient patient:patients){
            //根据dict中的内容赋予具体的值
            //地址
            patient.setCertificatesType(dictFeignClient.getName(patient.getCertificatesType()));
            //todo 其他的复制上面的就可以了，依次设置好
        }


        return patients;

    }

    @Override
    public void savePatient(Patient patient) {
        patientMapper.insert(patient);
    }

    @Override
    public Patient getPatientId(Long id) {

    }

    //Patient对象里面其他参数封装
    private Patient packPatient(Patient patient) {

    }
}
