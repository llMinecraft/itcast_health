package com.itheima.service;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    public void add(CheckItem checkItem);
    // 分页查询
    PageResult pageQuery(QueryPageBean queryPageBean);
    // 检查项删除
    public void deleteById(Integer id);
    // 编辑区
    public void edit(CheckItem checkItem);

    //回显
    public CheckItem findById(Integer id);

    //检查组查找所有
    public List<CheckItem> findAll();
}
