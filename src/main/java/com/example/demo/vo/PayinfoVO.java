package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //카멜케이스 //생성자 // _ (다시)있어서 오류나면 생성자 생성하기. 
public class PayinfoVO {

    int payNum;
    int paid_amount;
    String imp_uid;

    //생성자
    // public PayinfoVO(int payNum, int paid_amount, String imp_uid) {
    //     this.payNum = payNum;
    //     this.paid_amount = paid_amount;
    //     this.imp_uid = imp_uid;
    // }






}

    
