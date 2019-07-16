package com.boot.example.demo.Controller;

import com.boot.example.demo.dto.AccessTokenDTO;
import com.boot.example.demo.dto.GithubUser;
import com.boot.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    @Autowired

    private GithubProvider githubProvider;

    @Value("${github.client_id}")
    private String client_id;

    @Value("${github.client_secret}")
    private String client_secret;

    @Value("${github.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/back")
    //@RequestParam()    接收网页传递的参数
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state" ) String state,
                           HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);  //利用githubProvider方法来获取token
        GithubUser user = githubProvider.getUser(accessToken);    //利用token 来获取其中的值

        if(user != null){
            //登录成功
            request.getSession().setAttribute("user",user);

            return "redirect:/index";   //redirect  显示的一个路径 所以要加上/index 才能表示主页
        }else{
            //登录失败


            return "redirect:/index";
        }
    }


}