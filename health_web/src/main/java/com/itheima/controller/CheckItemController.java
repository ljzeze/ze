package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/check")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public Result findAll(){
      List<CheckItem> list= checkItemService.findAll();
      return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }
}
