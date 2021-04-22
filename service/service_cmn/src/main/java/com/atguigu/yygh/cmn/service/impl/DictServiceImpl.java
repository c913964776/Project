package com.atguigu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xerces.internal.xs.LSInputList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    //根据数据id查询子数据列表
    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> findChildById(Long id) {
        QueryWrapper<Dict> wrapper=new QueryWrapper<>();
        QueryWrapper<Dict> wrapperExa= wrapper.eq("parent_id",id);
        List<Dict> list= dictMapper.selectList(wrapperExa);
        //向list集合中每个dict对象设置hasChild
        for(Dict dict:list){
            if(hasChild(dict.getId())){
                dict.setHasChildren(true);
            }

            else dict.setHasChildren(false);

        }

        return list;
    }

    //判断id下面是否有子数据
    public Boolean hasChild(Long id){
        QueryWrapper<Dict> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = dictMapper.selectCount(wrapper);
        return count>0;
    }




    //导出数据字典接口
    @Override
    public void exportDiceData(HttpServletResponse response){
        //设置下载信息
       //response.setContentType(MIME)的作用是使客户端浏览器，区分不同种类的数据，
        // 并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据。
        response.setContentType("application/vnd.ms-excel");
        String fileName="dict";
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
        //查询数据库 list里面放的是Dict
        List<Dict> list =dictMapper.selectList(new QueryWrapper<Dict>());
        List<DictEeVo> voList=new ArrayList<>();
        for(Dict dict: list){
            DictEeVo vo=new DictEeVo();
            BeanUtils.copyProperties(dict,vo);
            voList.add(vo);
        }


        //调用方法实现写操作
        EasyExcel.write(fileName, DictEeVo.class).sheet("dict")
                .doWrite(voList);


    }


    //导入数据字典接口

    @Override
    public void importDictData(MultipartFile file) {
        // 读取文件路径
        String fileName = "F:\\excel\\01.xlsx";
        //调用方法实现读取操作
        EasyExcel.read(fileName, DictEeVo.class,new DictListener(dictMapper)).sheet().doRead();
    }


//    @Override
//    public void exportDictData(HttpServletResponse response) {
//        //设置下载信息
//        //response.setContentType(MIME)的作用是使客户端浏览器，区分不同种类的数据，
//        // 并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据。
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        String fileName = "dict";
//        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
//        //查询数据库
//        List<Dict> dictList = baseMapper.selectList(null);
//        //Dict -- DictEeVo
//        List<DictEeVo> dictVoList = new ArrayList<>();
//        for(Dict dict:dictList) {
//            DictEeVo dictEeVo = new DictEeVo();
//           // dictEeVo.setId(dict.getId());
//            BeanUtils.copyProperties(dict,dictEeVo);
//            dictVoList.add(dictEeVo);
//        }
//        //调用方法进行写操作
//        try {
//            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
//                    .doWrite(dictVoList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //导入数据字典
//    @Override
//    @CacheEvict(value = "dict", allEntries=true)
//    public void importDictData(MultipartFile file) {
//        try {
//            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //根据dictcode和value查询



    //根据dictCode获取下级节点


    //判断id下面是否有子节点

}
