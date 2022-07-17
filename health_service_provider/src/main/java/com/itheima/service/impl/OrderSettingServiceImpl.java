package com.itheima.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    //批量导入预约设置数据
    @Override
    public void add(List<OrderSetting> list) {
        //判断添加对象是否为空
        if(list!=null&&list.size()>0){
            //遍历对象列表
            for(OrderSetting orderSetting:list){
                long countByOrderDao =
                        orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                //如果传过来两次就是编辑
                if(countByOrderDao>0){
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {//否则就是添加
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {

        String begin = date+"-1";//2022-6-1
        String end = date +"-31";//2022-6-31

        Map map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
            for(OrderSetting orderSetting:list){
                Map  m  = new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate());
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }

        return result;
    }

    //根据日期设置人数

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //首先根据日期查寻是否已经预约设置
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        System.out.println(count);
        if (count > 0) {
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
