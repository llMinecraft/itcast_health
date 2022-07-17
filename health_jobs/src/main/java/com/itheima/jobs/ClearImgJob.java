package com.itheima.jobs;


import com.itheima.constant.RedisContstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

//自定义job,实现定时清理垃圾图片
public class ClearImgJob {
    //操作Redis数据库
    @Autowired
    private JedisPool jedisPool;
    public void clearImg(){
        Set<String> set = jedisPool.getResource()
                .sdiff(RedisContstant.SETMEAL_PIC_RESOURCES,RedisContstant.SETMEAL_PIC_DB_RESOURCES);
        if(set!=null){
            for(String picName:set){
                //删除七牛云服务器上的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片名称
                jedisPool.getResource().srem(RedisContstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("自定义任务执行，清理垃圾图片:"+picName);
            }
        }
    }
}
