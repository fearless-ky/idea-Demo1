package com.boot.example.demo.interceptor;

import com.boot.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.boot.example.demo.model.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ThemeInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();//从网页中得到cookies存入到Cookie数组
        if(cookies != null && cookies.length != 0)
            for(Cookie cookie:cookies)                    //循环Cookie
            {
                if(cookie.getName().equals("token"))        //判断是否有名字是token的
                {
                    String token = cookie.getName();          //如果有定义一个token   来获取这个token的名字

                    User user = userMapper.findToken(cookie.getValue()); //调用UserMapper中的findToken()方法来判断数据库中是否有token值和cookie.getValue相同
                    if(user != null)
                    {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
