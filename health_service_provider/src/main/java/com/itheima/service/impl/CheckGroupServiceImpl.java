package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass =CheckGroupService.class) //对标Spring 的 @Service 这里的@Service是Dubbo包下的
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired //@Autowired叫做自动获取 Bean，
    private CheckGroupDao checkGroupDao;

        //检查组添加项
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组，操作t_checkgroup表
        checkGroupDao.add(checkGroup);
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem表
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    //检查组分页查询
    public PageResult queryPage(QueryPageBean queryPageBean) {
        //将前端传送过来的当前页赋值
        Integer currentPage = queryPageBean.getCurrentPage();
        //将前端页面显示条目赋值
        Integer pageSize = queryPageBean.getPageSize();
        //将前端搜索值赋值给queryString
        String queryString = queryPageBean.getQueryString();
        //完成分页查询，基于mybatis框架提供的分页助手插件完成
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();
        return new PageResult(total,rows);
    }

    //根据id查找

    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }
    //关联查找
    public List<Integer> ItemIdsAndGropuId(Integer id) {
        return checkGroupDao.ItemIdsAndGropuId(id);
    }

    public void edit(CheckGroup checkGroup,Integer[] checkitemIds) {
        //首先检查组基本信息，操作检查组t_checkgroup表
        checkGroupDao.edit(checkGroup);
        //清理当前检查组关联的检查项，操作中间关系表t_checkgroup_checkitem表
        checkGroupDao.deleteAssoication(checkGroup.getId());
        //重新建立当前检查组和检查项的关联关系
        Integer checkGroupId = checkGroup.getId();
        setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    //建立检查组合检查项多对多关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if(checkitemIds!=null && checkitemIds.length>0){
            for (Integer checkitemId: checkitemIds) {
                Map<String,Integer> map = new HashMap<String, Integer>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
    //删除

    public void delete(Integer id) {
        //先进行判断该组id是否存在关联
        if(checkGroupDao.findId(id)>0){
            //存在关联就执行删除关联语句，不存在就不执行
            checkGroupDao.deleteAssoication(id);
        }
        //再删除项目组
        checkGroupDao.delete(id);
    }

    //查询所有
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
