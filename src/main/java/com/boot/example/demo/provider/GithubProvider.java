package com.boot.example.demo.provider;

import com.alibaba.fastjson.JSONObject;
import com.boot.example.demo.dto.AccessTokenDTO;
import com.boot.example.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Component
public class GithubProvider {

    public  String getAccessToken(AccessTokenDTO accessTokenDTO){

        //利用okhttp技术 来个获取网页中的token

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSONObject.toJSONString(accessTokenDTO) );
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string =  response.body().string();
            String token = string.split("&")[0].split("=")[1];   //分割字符
            return token;

        } catch (Exception e){
            e.printStackTrace();
        }
            return null;
    }
    public GithubUser getUser(String accessToken)
    {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSONObject.parseObject(string,GithubUser.class); //把string的JSON对象装换为java的类对象

            return githubUser;
        } catch (IOException e) {
        }
      return null;
    }

}
