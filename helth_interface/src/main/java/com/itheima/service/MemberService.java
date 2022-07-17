package com.itheima.service;

import com.itheima.constant.MessageConstant;
import com.itheima.pojo.Member;

import java.util.List;

public interface MemberService {
    Member findByTelephone(String telephone);
    public void add(Member member);
    List<Integer> findMemberCountBymounth(List<String> months);
}
