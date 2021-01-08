package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    /**
     * 新增
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 根据套餐id查询检查组id
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId,@Param("checkgroupId") Integer checkgroupId);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findByCondition(String queryString);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 根据套餐id查询有关联的检查组id
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     *  更新套餐信息
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 更新前删除套餐与检查组关联
     * @param setmealId
     */
    void deleteSetmealCheckGroup(Integer setmealId);

    /**
     * 查询套餐与订单是否有关联
     * @param id
     * @return
     */
    int findOrderCountBySetmealId(int id);

    /**
     * 根据id删除套餐数据
     * @param id
     */
    void deleteById(int id);
}
