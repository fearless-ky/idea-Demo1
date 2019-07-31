package com.boot.example.demo.Controller;

import com.boot.example.demo.Mapper.UserMapper;
import com.boot.example.demo.dto.PageactionDTO;
import com.boot.example.demo.dto.QuestionDTO;
import com.boot.example.demo.model.User;
import com.boot.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController implements UserMapper{

  @GetMapping("/hello")
    public String hello(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
      model.addAttribute("name", name);
      return "hello";
  }

  @Autowired
  private UserMapper userMapper;
  @Autowired
  private QuestionService questionService;

  @GetMapping("/index")
  public String zhuye(HttpServletRequest request, Model model,
                      @RequestParam(name = "page",defaultValue = "1") Integer page,
                      @RequestParam(name = "size",defaultValue = "3") Integer size){   //定义一个request来获取  如果没有参数传入   就默认值是defaultValue

/*   Cookie[] cookies = request.getCookies();//从网页中得到cookies存入到Cookie数组
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
    }*/
      PageactionDTO pageactionDTO = questionService.list(page,size);
      model.addAttribute("pageactionDTO",pageactionDTO);
    return "index";
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

  @Override
  public User findByID(Integer id) {
    return null;
  }

}
