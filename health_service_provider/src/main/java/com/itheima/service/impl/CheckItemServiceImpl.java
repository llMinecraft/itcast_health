package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service
@Service(interfaceClass = CheckItemService.class)//Dubbo的远程服务注解 不是 spring中的service*/
@Transactional //事物处理注解
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
    //检查项分页查询
    public PageResult  pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //完成分页查询，基于mybatis框架提供的分页助手插件完成
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total,rows);
    }
    //根据id删除
    public void deleteById(Integer id) {
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count>0){
            //当前检查项已经被检查组关联，不予许删除
            new RuntimeException();
        }
        checkItemDao.deleteById(id);
    }

    //CheckItem编辑区
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }
    //检查组查找所有
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
