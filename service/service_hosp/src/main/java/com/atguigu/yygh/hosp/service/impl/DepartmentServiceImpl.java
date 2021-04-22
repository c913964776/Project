package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.DepartmentRepository;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;

import com.atguigu.yygh.vo.hosp.DepartmentVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.javassist.runtime.Desc;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    //上传科室接口
    @Override
    public void save(Map<String, Object> paramMap) {
        //先把传进来的map变成对象
        Department department = JSONObject.parseObject(JSON.toJSONString(paramMap), Department.class);

        //获取hoscode以及depcode
        String hoscode=department.getHoscode();
        String depcode=department.getDepcode();
        Department targetdepartment=departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode,depcode);
        //检查有无重复  重复就更新  没有重复就直接插入
        if(StringUtils.isEmpty(targetdepartment)){
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
        }
        else if (!StringUtils.isEmpty(targetdepartment)){
            BeanUtils.copyProperties(department,targetdepartment);
        }
        departmentRepository.save(department);

    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        //这是mangodb的分页查询  和mysql很不一样的哈  不是用的mybatis
        //创建page对象，设置当前页以及每页记录数
        Pageable pageable=PageRequest.of(page-1,limit);
        
        //创建example对象  传递条件
        ExampleMatcher matching = ExampleMatcher.matching();

        Sort sort=Sort.by(Sort.Direction.DESC,"creatTime");



    }

    //删除科室接口
    @Override
    public void remove(String hoscode, String depcode) {
        //根据医院编号 和 科室编号查询
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null) {
            //调用方法删除
            departmentRepository.deleteById(department.getId());
        }
    }

    //根据医院编号，查询医院所有科室列表
    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建list集合，用于最终数据封装
        List<DepartmentVo> result = new ArrayList<>();

        //根据医院编号，查询医院所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example example = Example.of(departmentQuery);
        //所有科室列表 departmentList
        List<Department> departmentList = departmentRepository.findAll(example);

        //根据大科室编号  bigcode 分组，获取每个大科室里面下级子科室
        Map<String, List<Department>> deparmentMap =
                departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        //遍历map集合 deparmentMap
        for(Map.Entry<String,List<Department>> entry : deparmentMap.entrySet()) {
            //大科室编号
            String bigcode = entry.getKey();
            //大科室编号对应的全局数据
            List<Department> deparment1List = entry.getValue();
            //封装大科室
            DepartmentVo departmentVo1 = new DepartmentVo();
            departmentVo1.setDepcode(bigcode);
            departmentVo1.setDepname(deparment1List.get(0).getBigname());

            //封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for(Department department: deparment1List) {
                DepartmentVo departmentVo2 =  new DepartmentVo();
                departmentVo2.setDepcode(department.getDepcode());
                departmentVo2.setDepname(department.getDepname());
                //封装到list集合
                children.add(departmentVo2);
            }
            //把小科室list集合放到大科室children里面
            departmentVo1.setChildren(children);
            //放到最终result里面
            result.add(departmentVo1);
        }
        //返回
        return result;
    }

    //根据科室编号，和医院编号，查询科室名称
    @Override
    public String getDepName(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null) {
            return department.getDepname();
        }
        return null;
    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
    }

}
