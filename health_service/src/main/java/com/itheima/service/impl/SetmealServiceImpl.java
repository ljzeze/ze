package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 新增
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional//处理两个以上的事务，要添加事务注解
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        if (checkgroupIds != null){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (queryPageBean.getQueryString() !=null){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<Setmeal>(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    /**
     * 根据套餐id查询有关联的检查组id
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    /**
     * 更新套餐信息
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //调用dao方法更新套餐数据
        setmealDao.update(setmeal);
        //获取setmeal的id
        Integer setmealId = setmeal.getId();
        //删除更新前的套餐与检查组关联
        setmealDao.deleteSetmealCheckGroup(setmealId);
        //判断checkgroupIds不为空
        if (checkgroupIds != null){
            //遍历
            for (Integer checkgroupId : checkgroupIds) {
                //查询勾选套餐与检查组的关联
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }
    }

    /**
     * 根据id删除套餐数据
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        //查询套餐与订单是否有关联
     int count = setmealDao.findOrderCountBySetmealId(id);
     if (count > 0){
         //有关联就抛运行时异常
         throw new HealthException("套餐与订单有关联，不能删除");
     }
     //先删除套餐与检查组的关联
        setmealDao.deleteSetmealCheckGroup(id);
     //根据id删除套餐数据
     setmealDao.deleteById(id);
    }
}
