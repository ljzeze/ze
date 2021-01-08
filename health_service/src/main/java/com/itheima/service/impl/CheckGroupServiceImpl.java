package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    /**
     * 新增
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
      checkGroupDao.add(checkGroup);
      //获取检查组id
        Integer checkGroupId =checkGroup.getId();
        //判断检查项id数组不为空，再遍历检查项id，最后关联检查组
        if (checkitemIds!=null){
            for (Integer checkItemsId : checkitemIds) {
                Map map = new HashMap();
                map.put("groupId",checkGroup.getId());
                map.put("checkitemId",checkItemsId);
                checkGroupDao.addCheckItemCheckGroup(map);
            }
        }
    }

    /**
     * 分页查询检查组
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //使用PageHelper.startPage获取当前当前页数和每页记录数
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断查询条件不为空
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())){
            //不为空则模糊查询
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //调用dao方法根据条件查询和查询到的所有的数据分页
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup> (page.getTotal(),page.getResult());
    }

    /**
     * 根据检查组id查询数据
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 根据检查组id查询检查项id
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 编辑更新检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override

    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {

        //更新检查组信息
        checkGroupDao.update(checkGroup);
        //删除更新前的检查组关联的检查项
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());

        //判断checkitemIds不为空
        if (checkitemIds !=null){
            //遍历
            for (Integer checkitemId : checkitemIds) {
                //添加与检查组有关联的检查项id
                Map map = new HashMap();
                map.put("groupId",checkGroup.getId());
                map.put("checkitemId",checkitemId);
                checkGroupDao.addCheckItemCheckGroup(map);
            }
        }
    }

    /**
     * 删除检查组
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id){
        //判断检查组是否与套餐有关联
        int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if (count >0){
            throw new HealthException("该数据与套餐有关联，不能删除");
        }
        //删除检查组与检查项的关联
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //最后删除检查组
        checkGroupDao.deleteById(id);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
     return  checkGroupDao.findAll();
    }
}
