package com.example.demo.controller;

import okhttp3.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportController {


    String impKey = "6510774276607636"; // 아임포트 인증키를 입력해 주세요.
    String impSecret = "hzGO6aM6m3xfCHWeXIUmOHf0gPbIjUtLF1lU9Otr48iYpF9DeZWSyLXiqZYYxb5ZpsTtQa1RWMLGgOnn"; // 아임포트 시크릿키를 입력해 주세요.
    String url = "https://api.iamport.kr/users/getToken"; //아임포트에서 정한 토큰 가져오는 url
    String accessToken= "";

    //토큰 값 가져오기
    public ImportController() {
        
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"imp_key\":\""+impKey+"\",\"imp_secret\":\""+impSecret+"\"}");
        Request request = new Request.Builder()
            .url(url)
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build();
        try {
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();

                // JSON 텍스트를 파싱
                JSONObject json = new JSONObject(responseData);

                // JSON 객체에서 access_token 값을 가져옴
                accessToken = json.getJSONObject("response").getString("access_token"); //JSON에서 response에서 access_token만 가져와 String에 저장한다.

                System.out.println(accessToken); // access_token 값 출력

                //response 전체값을 받으려면
                // responseData = response.body().string();
                // System.out.println("response:"+responseData); // 인증 토큰이 담긴 JSON 데이터가 출력됩니다.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/tests")
    public String callApiWithJson() {

    StringBuffer result = new StringBuffer();

        String jsonPrintString = null;

        try {
            //매번 다르게 생성되는 accessToken으로 모든 결제정보를 확인할 수 있는 경로
            String apiUrl = "https://api.iamport.kr/payments/status/all?limit=20&sorting=-started&_token="+accessToken; 

            URL url = new URL(apiUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }
            jsonPrintString = result.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonPrintString;
    }

    @GetMapping("/unitpay/{imp_uid}")
	public String paySuccessInfo(@PathVariable(value = "imp_uid") String imp_uid, Model model ){
        System.out.println("fdgdfgfdgdsgsgsfdgsdg");
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;

        try {
            //token값과 결제완료 후 발급되는 고유uid를 이용하여 개별 결제정보를 확인하는 url
            String apiUrl = "https://api.iamport.kr/payments/"+imp_uid+"?_token="+accessToken;

            URL url = new URL(apiUrl);

            //url열결하기
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }
            jsonPrintString = result.toString();
            JSONObject json = new JSONObject(jsonPrintString);

            //list가 아니라 각 값을 넘기고 싶었는데 그건 못했다.ㅜㅜ
            int amount = json.getJSONObject("response").getInt("amount");
            String buyer_name = json.getJSONObject("response").getString("buyer_name");
            String emb_pg_provider = json.getJSONObject("response").getString("emb_pg_provider");
            String status = json.getJSONObject("response").getString("status");
            String buyer_addr = json.getJSONObject("response").getString("buyer_addr");

            System.out.println("stringbuilder       "+amount);

            // accessToken = json.getJSONObject("response").getString("access_token");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonPrintString;

        // model.addAttribute("imp_uid", imp_uid);
		// return "paySuccess";
	}
    
}



// 