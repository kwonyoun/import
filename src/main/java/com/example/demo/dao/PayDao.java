package com.example.demo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.PayinfoVO;

@Mapper
public interface PayDao {
    
    //Dao
    public void paySuccessJSON(Map<String, Object> map);
    public void paySuccessVO(PayinfoVO vo);
    public void paySuccessParam(Map<String, Object> map);

    public PayinfoVO payInfoSelect(String uid);
    
}
