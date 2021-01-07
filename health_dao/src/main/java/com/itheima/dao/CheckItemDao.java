package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    Page<CheckItem> findPage(String queryString);

    int findCountByCheckItem(int id);

    void deleteById(int id);

    void update(CheckItem checkItem);

    CheckItem findById(int id);
}
