package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CheckItemDao{

    public void add(CheckItem checkItem);

    // 查询检查项数据
    Page<CheckItem> selectByCondition(String queryString);

    //检查项判断删除
    public long findCountByCheckItemId(Integer checkitem_id);
    public void deleteById(Integer id);

    //回显数据
    public CheckItem findById(Integer id);

    //检查项编辑区
    public void edit(CheckItem checkItem);

    //检查组查询所有
    public List<CheckItem> findAll();

    public CheckItem findCheckItemById(int id);

}
