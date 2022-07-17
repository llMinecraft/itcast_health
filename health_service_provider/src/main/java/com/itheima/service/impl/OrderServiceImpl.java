package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.api.R;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;



@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Override
    public Result order(Map map) throws Exception{
        String orderDate = (String) map.get("orderDate");//预约日期
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if(orderSetting == null){
            //指定日期没有进行预约设置，无法完成体检预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2.检查用户所选择的预约日期是否已经约满。
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if(reservations >=number){
            return new Result(false,MessageConstant.ORDER_FULL);
        }

        //检查预约日期是否预约已满
        String telephone =(String) map.get("telephone");
        Member member  = memberDao.findByTelephone(telephone);

        //防止重复预约
        if(member!=null){
            try {
                Integer memberId = member.getId();
                Date order_Date = DateUtils.parseString2Date(orderDate);
                String setmealId= (String)map.get("setmealI=Id");
                Order order = new Order(memberId, order_Date, Integer.parseInt(setmealId));
                //根据条件进行查询
                List<Order> list = orderDao.findByCondition(order);
                if(list !=null && list.size()>0){
                    //说明用户在重复预约，无法完成再次预约
                    return new Result(false,MessageConstant.HAS_ORDERED);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //当前用户不是会员，需要添加到会员表
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);//自动会员
        }

        //5.预约成功，更新当日的已月月人数
        //保存预约信息到预约表
        Order order = new Order(member.getId(),
                DateUtils.parseString2Date(orderDate),
                (String)map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);

        orderSetting.setReservations(orderSetting.getReservations()+1);//设置已完成人数+1
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }
    //根据ID查找数据

    @Override
    public Map findById(Integer id) {
       Map map = orderDao.findById4Detail(id);
       if(map!=null){
           Date orderDate = (Date) map.get("orderDate");
           try {
               map.put("orderDate",DateUtils.parseDate2String(orderDate));

           } catch (Exception e) {
               e.printStackTrace();
           }

       }
       return map;
    }
}
