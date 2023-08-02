package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    //결제페이지에서 버튼을 누르면 실행되는 ajax. 결제가 성공하면 값을 데이터베이스에 저장한다. 
    @ResponseBody
	@RequestMapping(value="/paySuccess", method=RequestMethod.POST)
	public String paySuccess(@RequestParam("paid_amount")String paid_amount,@RequestParam("imp_uid")String uid) {

		System.out.println("controller/총합계:"+paid_amount+"/고유id:"+uid);

		Map<String, Object> map = new HashMap<>();

        //결제값을 형변환한다.
        int amount = Integer.parseInt(paid_amount);

		map.put("imp_uid", uid);
		map.put("amount", amount);

        //생성된 고유 uid와 결제값인 amount를 저장하는 메소드.
        svc.paySuccess(map);

        //고유uid를 return하여 ajax성공시 실행되는 function으로 이동하여 url로 바로 이동한다. 
        return uid;
			
	}

    //결제성공 시 고유uid를 받아 이 메소드로 온다. 
    @GetMapping("/payInfo/{res}")
    public String payInfo(@PathVariable("res") String uid, Model model){
        System.out.println("payInfo");

        PayinfoVO vo = svc.payInfoSelect(uid);
        model.addAttribute("vo", vo);

        //결제완료 페이지로 이동한다. 
        return "paySuccess";

    }

    
}
