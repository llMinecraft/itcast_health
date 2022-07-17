package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {

    public void add(CheckGroup checkGroup, Integer[] checkitemIds);
    //检查组分页查询
    public PageResult queryPage(QueryPageBean queryPageBean);

    public CheckGroup findById(Integer id);

    public void edit(CheckGroup checkGroup,Integer[] checkitemIds);

    public List<Integer> ItemIdsAndGropuId(Integer id);

    public void delete(Integer id);

    public List<CheckGroup> findAll();
}
