package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.PayService;
import com.example.demo.vo.PayinfoVO;

@Controller
public class PayController {

    @Autowired
    PayService svc;

    @GetMapping("/pay")
    public String pay() {
        return "pay";
    }

    //결제페이지에서 버튼을 누르면 실행되는 ajax.
    //Json을 Map형태로 변환
    //@RequestBody를 붙으면 ajax에서 받아온 값이 자동으로 map으로 변환되어 해당 변수로 저장된다.
    // @ResponseBody
	// @RequestMapping(value="/paySuccessJSON", method=RequestMethod.POST)
	// public String paySuccessJSON(@RequestBody HashMap<String, Object> map) {

    //     //test
    //     System.out.println("paySuccessJSON");
    //     System.out.println(map);
    //     // String name = map.get("name").toString();
    //     // System.out.println("name:"+name);

    //     //return값으로 고유 uid를 반환하기.
    //     String imp_uid = map.get("imp_uid").toString();

    //     svc.paySuccessJSON(map);

    //     return imp_uid;
	// }

    @ResponseBody
	@RequestMapping(value="/paySuccessVO", method=RequestMethod.POST)
	public String paySuccessVO(@RequestBody PayinfoVO vo) {

        //test
        System.out.println("paySuccessVO");
        System.out.println(vo);
        // // String name = vo.getName();
        // System.out.println("name:  "+name);

        String imp_uid = vo.getImp_uid();
        int paid_amount = vo.getPaid_amount();
        System.out.println("imp_uid:   "+imp_uid);
        System.out.println("paid_amount:   "+paid_amount);

        svc.paySuccessVO(vo);

        return imp_uid;
	}

    // @ResponseBody
	// @RequestMapping(value="/paySuccessParam", method=RequestMethod.POST)
	// public String paySuccessParam(@RequestParam("paid_amount")String amount,@RequestParam("imp_uid")String uid) {

    //     System.out.println("paySuccessParam");
	// 	Map<String, Object> map = new HashMap<>();

    //     //결제값을 형변환한다.
    //     int paid_amount = Integer.parseInt(amount);

	// 	map.put("imp_uid", uid);
	// 	map.put("paid_amount", paid_amount);

    //     //생성된 고유 uid와 결제값인 amount를 저장하는 메소드.
    //     svc.paySuccessParam(map);

    //     //고유uid를 return하여 ajax성공시 실행되는 function으로 이동하여 url로 바로 이동한다. 
    //     return uid;
			
	// }

    //결제성공 시 고유uid를 받아 이 메소드로 온다. 
    @GetMapping("/payInfo/{res}")
    public String payInfo(@PathVariable("res") String imp_uid, Model model){
        System.out.println("payInfo");
        System.out.println("uid:  "+imp_uid);

        PayinfoVO vo = svc.payInfoSelect(imp_uid);

        model.addAttribute("vo", vo);

        //결제완료 페이지로 이동한다. 
        return "paySuccess";

    }

    
}
