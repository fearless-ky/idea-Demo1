package com.boot.example.demo.Controller;

import com.boot.example.demo.Mapper.UserMapper;
import com.boot.example.demo.model.User;
import com.boot.example.demo.dto.AccessTokenDTO;
import com.boot.example.demo.dto.GithubUser;
import com.boot.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController implements UserMapper{

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    private  static int num=3;

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
        GithubUser githubUser = githubProvider.getUser(accessToken);    //利用token 来获取其中的值

        if(githubUser != null){
            //登录成功
           User user = new User();
           user.setId(num);
           user.setToken(UUID.randomUUID().toString());
           user.setName(githubUser.getName());
           user.setAccount_id(String.valueOf(githubUser.getId()));
           user.setGmt_create(System.currentTimeMillis());
           user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);

            return "redirect:/index";   //redirect  显示的一个路径 所以要加上/index 才能表示主页
        }else{
            //登录失败

            return "redirect:/index";
        }
    }

    @Override
    public void insert(User user) {

    }
}
