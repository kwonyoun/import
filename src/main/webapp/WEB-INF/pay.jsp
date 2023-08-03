<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jQuery -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
    <!-- iamport.payment.js -->
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <!-- PortOne SDK -->
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
    <!-- <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> -->

    <script>
        
        function requestPay() {
        IMP.init('imp71853610'); //iamport 대신 자신의 "가맹점 식별코드"를 사용
        IMP.request_pay({
            pg: "INIpayTest",
            pay_method: "card",
            merchant_uid : 'merchant_'+new Date().getTime(),
            name : '결제테스트',
            amount : 100,
            buyer_email : 'iamport@siot.do',
            buyer_name : '구매자',
            buyer_tel : '010-1234-5678',
            buyer_addr : '서울특별시 강남구 삼성동',
            buyer_postcode : '123-456'
        }, function(data){
            if(data.success){
                var msg = "결제 완료";
                msg += '고유ID : ' + data.imp_uid;                //아임포트 uid는 실제 결제 시 결제 고유번호를 서버와 비교해서 결제처리하는데 필요없긴함.
                msg += '// 상점 거래ID : ' + data.merchant_uid;
                msg += '// 결제 금액 : ' + data.paid_amount;
                msg += '// 카드 승인번호 : ' + data.apply_num;

                var ordData = {
                // "name" : data.name,
                "imp_uid" : data.imp_uid,
                // "merchant_uid" : data.merchant_uid,
                "paid_amount" : data.paid_amount
                // "apply_num" : data.apply_num,
                // "buyer_email" : data.buyer_email,
                // "buyer_name" : data.buyer_name,
                // "buyer_tel" : data.buyer_tel,
                // "buyer_addr" : data.buyer_addr,
                // "buyer_postcode" : data.buyer_postcode
            };
                
                $.ajax({
                    type : 'post',
                    url : '/paySuccessVO',
                    contentType: 'application/json; charset=utf-8', 
                    data : JSON.stringify(ordData),
                    success: function(res) {
                        alert("ajax-success   "+res);
                        document.location.href="/payInfo/"+res;
                        
                    },
                    error: function(xhr, status, error) {
                        console.error(error);
                    }
                });
                alert(msg);
                
            }else{
                var msg = "결제 실패"
                msg += "에러 내용" + rsp.error_msg;
                alert(msg);
                document.location.href="/pay";
            }
            
        });

        }
        </script>

    <meta charset="UTF-8">
    <title>Sample Payment</title>
</head>
<body>
    <button onclick="requestPay()">결제하기</button> <!-- 결제하기 버튼 생성 -->
</body>
</html>