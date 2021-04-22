package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import rx.internal.util.unsafe.MpmcArrayQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;


    //上传医院API
    @PostMapping("saveHospital")
    public Result save(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();=request.getParameterMap()
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);

        //在客户传过来的map中找到hoscode，与hosset中的code进行对比
        String hoscode=(String)map.get("hoscode");
        if(StringUtils.isEmpty(hoscode)){
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        if(HttpRequestHelper.isSignEquals())


        hospitalService.save(map);
        return Result.ok();
    }






    //医院列表(条件查询分页)
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable(value = "page") Integer page , @PathVariable(value = "limit") Integer limit ,HospitalQueryVo hospitalQueryVo) {
        //hosp还是存在mongodb中的，所以操作应该都遵循manggodb





    }

    //更新医院上线状态
    @ApiOperation(value = "更新医院上线状态")
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus() {

    }

    //医院详情信息
    @ApiOperation(value = "医院详情信息")
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail() {

    }
}
