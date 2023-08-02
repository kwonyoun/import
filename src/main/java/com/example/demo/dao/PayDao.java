package com.example.demo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.PayinfoVO;

@Mapper
public interface PayDao {
    
    //Dao
    public void paySuccess(Map<String, Object> map);
    public PayinfoVO payInfoSelect(String uid);
    
}
