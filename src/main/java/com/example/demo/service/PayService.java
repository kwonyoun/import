package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PayDao;
import com.example.demo.vo.PayinfoVO;

@Service
public class PayService {

    @Autowired
    PayDao dao;

    //Service
    public void paySuccessJSON(Map<String, Object> map){
        dao.paySuccessJSON(map);
    };

    public void paySuccessVO(PayinfoVO vo){
        dao.paySuccessVO(vo);
    };

    public void paySuccessParam(Map<String, Object> map){
        dao.paySuccessParam(map);
    };

    public PayinfoVO payInfoSelect(String uid){
        System.out.println("svc uid:  "+uid);
        PayinfoVO vo = dao.payInfoSelect(uid);

        System.out.println("vo:  "+vo);

        return vo;
    };
    
}
