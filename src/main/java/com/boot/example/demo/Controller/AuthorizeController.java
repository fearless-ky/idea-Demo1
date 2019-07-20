package com.boot.example.demo.Controller;

import com.boot.example.demo.Mapper.UserMapper;
import com.boot.example.demo.model.User;
import com.boot.example.demo.dto.AccessTokenDTO;
import com.boot.example.demo.dto.GithubUser;
import com.boot.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController implements UserMapper{

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

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
                           HttpServletRequest request,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();  //把网页返回来的code和state和固定的id、secret、uri 封装到实体类
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);  //利用githubProvider方法来获取token
        GithubUser githubUser = githubProvider.getUser(accessToken);    //利用token 来获取其中的值
        List<User> numAll = userMapper.searchAll();                     //查询数据库中有多少条数据

        if(githubUser != null){
            //登录成功
            String token = UUID.randomUUID().toString();
            int num = numAll.size();
            num++;
           User user = new User();
           user.setId(num);
           user.setToken(token);
           user.setName(githubUser.getName());
           user.setAccount_id(String.valueOf(githubUser.getId()));
           user.setGmt_create(System.currentTimeMillis());
           user.setGmt_modified(user.getGmt_create());
           user.setAvatarUrl(githubUser.getAvatar_url());
           userMapper.insert(user);
           response.addCookie(new Cookie("token",token));
            return "redirect:/index";   //redirect  显示的一个路径 所以要加上/index 才能表示主页
        }else{
            //登录失败

            return "redirect:/index";
        }
    }

    @Override
    public void insert(User user) {
    }
    @Override
    public User findToken(String token) {
        return null;
    }

    @Override
    public List<User> searchAll() {
        return null;
    }


}
