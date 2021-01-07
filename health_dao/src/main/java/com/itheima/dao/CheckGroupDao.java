package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    /**
     * 新增检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查项和检查组的关联，相同类型取别名
     * @param map
     */
    void addCheckItemCheckGroup(Map map);


    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

    /**
     * 根据检查组id查询数据
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 根据检查组id查询关联的检查项id
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 更新检查组
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 更新前删除与检查组有关联的检查项id
     * @param id
     */
    void deleteCheckGroupCheckItem(int id);

    /**
     * 查询检查组与套餐是否有关联
     * @param id
     * @return
     */
    int findSetmealCountByCheckGroupId(int id);

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(int id);
}
