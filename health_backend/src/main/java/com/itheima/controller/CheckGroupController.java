package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.api.R;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference //对标Spring的@Autowiret
    private CheckGroupService checkGroupService;

    //新增检查窗口
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try{
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    //检查组分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
           return  checkGroupService.queryPage(queryPageBean);
    }

    //数据回显
    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        if(checkGroup != null){
            Result result = new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
            return result;
        }
        return  new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
    //数据回显
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    //前端传过来CheckGroupId 是一对多的关系 对应多个CheckItem
    public Result ItemIdsAndGropuId(Integer id){
        try{
            List<Integer> checkitemIds = checkGroupService.ItemIdsAndGropuId(id);
            //返回的数据应该是一个数组，里面包含一对多CheckItem的id
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkitemIds);
        }catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try{
             checkGroupService.edit(checkGroup,checkitemIds);
             return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        }catch (Exception e){
           e.printStackTrace();
             return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    //删除检查组
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkGroupService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    //查询所有检查组
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupService.findAll());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }


}
