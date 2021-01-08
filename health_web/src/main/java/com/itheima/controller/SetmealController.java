package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    private static Logger log = LoggerFactory.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //获取原文件名
        String originalFilename = imgFile.getOriginalFilename();
        // 截取后缀名
        String sufffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 生成唯一id
        String uniqueId = UUID.randomUUID().toString();
        // 拼接唯一文件名
        String filename = uniqueId + sufffix;
        // 调用7牛工具上传图片
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), filename);
            //构建返回的数据
            Map<String, String> map = new HashMap<>(2);
            map.put("imgName", filename);
            map.put("domain", QiNiuUtils.DOMAIN);
            // 放到result里，再返回给页面
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 新增
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setmealService.add(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);

    }

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        Setmeal setmeal = setmealService.findById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("setmeal", setmeal);
        map.put("domain", QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, map);
    }

    /**
     * 根据套餐id查询与检查组有关联的id
     *
     * @param id
     * @return
     */
    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id) {
        List<Integer> checkGroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, checkGroupIds);
    }

    /**
     * 更新套餐信息
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        setmealService.update(setmeal,checkgroupIds);
        return new Result(true,"更新套餐成功");
    }

    /**
     * 根据id删除套餐数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        setmealService.deleteById(id);
        return new Result(true,"删除套餐成功");
    }
}
