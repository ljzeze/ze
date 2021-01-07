package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有不分页
     * @return
     */
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public Result findAll(){
      List<CheckItem> list= checkItemService.findAll();
      return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
     * 新增
     * @param checkItem
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
            checkItemService.add(checkItem);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);

    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
            PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    /**
     * 根据ID删除检查项信息
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
            checkItemService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 编辑修改
     * @param checkItem
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
            checkItemService.update(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 根据检查项id查询数据
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
       CheckItem checkItem = checkItemService.findById(id);
       return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

}
