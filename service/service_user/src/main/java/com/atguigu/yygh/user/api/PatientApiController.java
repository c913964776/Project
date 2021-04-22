package com.atguigu.yygh.user.api;

import com.atguigu.yygh.common.helper.JwtHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.AuthContextHolder;
import com.atguigu.yygh.model.user.Patient;
import com.atguigu.yygh.user.service.PatientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

//就诊人管理接口
@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {

    @Autowired
    private PatientService patientService;

    @Autowired

    //获取就诊人列表
    @GetMapping()
    public Result findAll(HttpServletRequest request) {
        //先获取用户的ID
        String token = request.getHeader("token");
        Long userId = JwtHelper.getUserId(token);

        //根据用户的id获取用户下面的病人
        List<Patient> list=new ArrayList<>();
        list= patientService.findAll(userId);

        return Result.ok(list);


    }

    //添加就诊人
    @PostMapping()
    public Result savePatient(@RequestBody Patient patient, HttpServletRequest request) {
        //先获取用户的id
        Long userId = AuthContextHolder.getUserId(request);
        patient.setId(userId);
        patientService.savePatient(patient);
        return Result.ok();
    }

    //根据id获取就诊人信息
    @GetMapping()
    public Result getPatient() {

    }

    //修改就诊人
    @PostMapping()
    public Result updatePatient(@RequestBody Patient patient) {

    }

    //删除就诊人
    @DeleteMapping()
    public Result removePatient(@PathVariable Long id) {

    }

    //根据就诊人id获取就诊人信息
    @GetMapping()
    public Patient getPatientOrder(@PathVariable Long id) {

    }
}
