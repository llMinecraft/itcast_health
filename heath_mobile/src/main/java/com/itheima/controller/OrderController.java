package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
/*    @Autowired
    private JedisPool jedisPool;*/
    @Reference
    private OrderService orderServer;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        String valicode = (String)map.get("validateCode");
        if (valicode.equals("0000")) {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);//设置预约类型，分为微信预约  还是电话约
            Result result = null;
            try {
                result = orderServer.order(map);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
        }else{
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

    }

    @RequestMapping("/findById")
    public Result findById(int id){

        try{
            Map map =  orderServer.findById(id);
            return new Result(false,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
         e.printStackTrace();
         return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }


    }
}
