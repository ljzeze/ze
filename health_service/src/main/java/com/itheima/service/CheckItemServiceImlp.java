package com.itheima.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.CheckItemDao;
import com.itheima.pojo.CheckItem;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImlp implements CheckItemService {
private CheckItemDao checkItemDao;
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
