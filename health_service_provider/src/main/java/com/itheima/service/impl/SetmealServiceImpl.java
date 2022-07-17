package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisContstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
/*     @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

   @Value("out_put_path")
    private String outputPath;

    //新增套餐，同时关联检查组
    public void generateHtml(String teplateName,String htmlPageName,Map map){


        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            Template template = configuration.getTemplate(teplateName);
            out = new FileWriter(new File(outputPath+"/"+"htmlPageName"));
            //输出文件
            template.process(map,out);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }*/
/*    //生成当前方法所需的静态页面
    public void generateMobileStaticHtml(){
        //需要生成套餐列表生成页面
        List<Setmeal> list = setmealDao.findAll();
        //需要生成套餐列表静态页面
        generateMobileSetmealListHtml(list);

        generateMobileSetmealDetailHtml( list);
    }
    //生成列表页面
    public void generateMobileSetmealListHtml(List<Setmeal> list){
        Map map = new HashMap();
        //为木板提供数据，用于生成静态页面
        map.put("setmealList",list);
        generateHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }
    //生成套餐详情页面
    public void generateMobileSetmealDetailHtml(List<Setmeal> list){
        for (Setmeal setmeal : list) {
            Map map = new HashMap();
            map.put("setmeal",setmealDao.findById(setmeal.getId()));
            generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);
        }
    }*/

    public void add(Setmeal setmeal, Integer[] checkgroupIds) {

        //在执行该方法后 里面的keyproperty 自动将id封装到对象当中.
        setmealDao.add(setmeal);
        if(checkgroupIds !=null && checkgroupIds.length>0){
            for (Integer ids: checkgroupIds)
            {
                Map<String,Integer> map = new HashMap<String, Integer>();
                map.put("setmeal_id",setmeal.getId());
                map.put("checkgroup_id",ids);
                setmealDao.addcheckgroupIds(map);
            }
        }
            String fileName = setmeal.getImg();
            if(fileName!=null){
                jedisPool.getResource().sadd(RedisContstant.SETMEAL_PIC_DB_RESOURCES,fileName);
            }
            //当添加
      //  generateMobileStaticHtml();
    }
    //查找
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }


    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }
}
