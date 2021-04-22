package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;


//swagger annotions
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
//@CrossOrigin
public class HospitalSetController {

    //注入service
    @Autowired
    private HospitalSetService hospitalSetService;

    // http://localhost:8201/admin/hosp/hospitalSet/findAll

    //1 查询医院设置表所有信息
    //swagger annotions
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping()
    public Result findAllSets() {
        List<HospitalSet> list= hospitalSetService.list();
        return Result.ok(list);
    }

//    //2 逻辑删除医院设置
//swagger annotions
    @ApiOperation(value = "delete hosp_set")
    @DeleteMapping("{id}")
    public Result deleteSetById(@PathVariable Integer id){
        boolean flag =hospitalSetService.removeById(id);
        return  Result.ok(flag);

    }

    //3 条件查询带分页 get
    @ApiOperation(value = "带条件的分页查询")
    @PostMapping("{page}/{limit}")
    public Result findPage(@PathVariable(value = "page") Long page,
                           @PathVariable(value = "limit") Long limit,
                           @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        //mybastis-plus中page的构造方法  传递参数为当前页数和总页数  cur and limit.
        //page1是新的page对象，pageHospital是结果page对象
       Page<HospitalSet> page1 =new Page<>(page,limit);

       //构造条件对象wrapper
        QueryWrapper<HospitalSet> wrapper=new QueryWrapper<>();
        String hosname=hospitalSetQueryVo.getHosname();
        String hoscode=hospitalSetQueryVo.getHoscode();
        if(!StringUtils.isEmpty(hosname)){
            wrapper.like("hosname",hospitalSetQueryVo.getHosname());
        }
        if(!StringUtils.isEmpty(hoscode)){
            wrapper.like("hoscode",hospitalSetQueryVo.getHoscode());
        }


        //hospitalsetservice把满足wrapper条件的数据都放到page1里，然后返回给pageHospitalSet
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page1,wrapper);
        return Result.ok(pageHospitalSet);



    }

    //    //4 添加医院设置
//    @PostMapping("saveHospitalSet")

    @ApiOperation(value = "添加医院设置")
    @PostMapping("addHosSet")
    public Result addHosSet(@RequestBody HospitalSet hospitalSet){
        //新增的set签名密钥和状态需要我们单独设置,即不在json数据里
        Random random=new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        hospitalSet.setStatus(1);
        //时间不需要手动添加，在mysql里面已经设置好了：当插入一条新数据的时候自动生成时间戳
        boolean flag= hospitalSetService.save(hospitalSet);
        if(flag==true){
            return  Result.ok();
        }
        else return Result.fail();
    }

    //根据id获得医院的设置
    @ApiOperation(value = "根据id获得医院的设置")
    @GetMapping("{id}")
    public Result findSetById(@PathVariable(value = "id") Long id ){
        HospitalSet hospitalSet=hospitalSetService.getById(id);
        if(hospitalSet==null){
            return Result.fail();
        }
        else return Result.ok(hospitalSet);
    }

    //修改医院的设置

    @ApiOperation(value = "根据id修改医院的设置")
    @PostMapping("{id}")
    public Result updateSetById(@RequestBody(required = true) HospitalSet hospitalSet,
                                @PathVariable(value = "id") Long id){
        hospitalSet.setId(id);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok(hospitalSet);

    }

    //批量删除医院的设置

    @ApiOperation(value = "批量删除医院的设置")
    @DeleteMapping()
    public Result deleteSetsByIds(@RequestBody List<Long> idList){
        boolean flag= hospitalSetService.removeByIds(idList);
        if (flag==true){
            return Result.ok();
        }
        else return Result.fail();
    }

    //医院设置的锁定和解锁 就是把status设置为0和1
    @ApiOperation(value = "医院状态设置")
    @PostMapping("/updatestatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id , @PathVariable Integer status){
        HospitalSet hospitalSet=hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }







//    @ApiOperation(value = "逻辑删除医院设置")
//    @DeleteMapping("{id}")
//    public Result removeHospSet(@PathVariable Long id) {
//
//    }
//
//    //3 条件查询带分页
//    @PostMapping("findPageHospSet/{current}/{limit}")
//    public Result findPageHospSet(@PathVariable long current,
//                                  @PathVariable long limit,
//                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
//
//    }
//
//    //4 添加医院设置
//    @PostMapping("saveHospitalSet")
//    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
//
//    }
//
//    //5 根据id获取医院设置
//    @GetMapping("getHospSet/{id}")
//    public Result getHospSet(@PathVariable Long id) {
////
//    }
//
//    //6 修改医院设置
//    @PostMapping("updateHospitalSet")
//    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
//
//    }
//
//    //7 批量删除医院设置
//    @DeleteMapping("batchRemove")
//    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
//
//    }
//
//    //8 医院设置锁定和解锁
//    @PutMapping("lockHospitalSet/{id}/{status}")
//    public Result lockHospitalSet(@PathVariable Long id,
//                                  @PathVariable Integer status) {
//
//    }
//
//    //9 发送签名秘钥
//    @PutMapping("sendKey/{id}")
//    public Result lockHospitalSet(@PathVariable Long id) {
//
//    }
}
