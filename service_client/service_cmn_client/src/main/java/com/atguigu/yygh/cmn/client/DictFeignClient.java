package com.atguigu.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-cmn")
@Repository
//feign相当于是一个controller
//通过hosp去调用cmn,所以配置的应该就是此处的cmn
//在hosp的service里面注入  DictFeignClient
public interface DictFeignClient {

    //根据dictcode和value查询
    @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")
    public String getName(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value);

    //根据value查询
    @GetMapping("/admin/cmn/dict/getName/{value}")
    public String getName(@PathVariable("value") String value);


}
