package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SetmealDao {
    //添加方法
    public void add(Setmeal setmeal);
    //拆分输入参数方法
    public void addcheckgroupIds(Map map);

    public Page<Setmeal> findByCondition(String queryString);

    public List<Setmeal> findAll();

    Setmeal findById(int id);

    List<Map<String, Object>> findSetmealCount();
}
