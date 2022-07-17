package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void setCheckGroupAndCheckItem(Map map);
    public Page<CheckGroup> selectByCondition(String queryString);
    public CheckGroup findById(Integer id);
    public void edit(CheckGroup checkGroup);
    public List<Integer> ItemIdsAndGropuId(Integer id);
    public void deleteAssoication(Integer id);
    public void delete(Integer id);
    public long findId(Integer id);
    public List<CheckGroup> findAll();
}
