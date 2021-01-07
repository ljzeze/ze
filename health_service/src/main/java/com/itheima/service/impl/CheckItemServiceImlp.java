package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImlp implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有，不分页
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 新增
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //第二种，Mapper接口方式的调用，推荐这种使用方式。
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //模糊查询
        //判断是否有查询条件
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())){
            //有条件，拼接%
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //查询语句分页
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());
        PageResult<CheckItem> pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 根据id删除检查项信息
     * @param id
     */
    @Override
    public void deleteById(int id) {
        //判断检查项是否被检查组使用
        //调用dao查询检查项的id是否在t_checkgroup_checkitem表中存在记录
     int count =  checkItemDao.findCountByCheckItem(id);
      if (count > 0 ){
            throw new HealthException("该检查项被使用，不能删除");
      }
      //到这里说明没有被使用，正常删除
        checkItemDao.deleteById(id);
    }

    /**
     * 编辑修改
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }


}
