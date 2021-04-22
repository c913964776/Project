package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/department")
//@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //根据医院编号，查询医院所有科室列表
    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }

    @ApiOperation(value = "上传科室")
    @PostMapping("uploaddep")
    public Result save(HttpServletRequest request){
        //将request中的json数据转变为map
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);

        //抛异常等操作..省略

        //保存
        departmentService.save(map);
        return Result.ok();


    }

    @ApiOperation(value ="根据条件查询科室")
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        //先获取医院的编号
        String hoscode = (String) map.get("hoscode");
        Integer page =(Integer) map.get("page");
        departmentService.findPageDepartment();

    }

}
