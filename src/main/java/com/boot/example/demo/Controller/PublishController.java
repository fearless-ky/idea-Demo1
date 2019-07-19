package com.boot.example.demo.Controller;

import com.boot.example.demo.Mapper.QuestionMapper;
import com.boot.example.demo.Mapper.UserMapper;
import com.boot.example.demo.model.Question;
import com.boot.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;
    @Autowired              //用来给指定的字段或方法注入所需的外部资源
    private QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }
    @PostMapping("/publish")
    public String dopublish(
            @RequestParam("title") String title,     //用RequestParam来得到网页传过来的title的值 放入定义的title中
            @RequestParam("description") String description,   //和request.getParameter("title");一样的 得到数据
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){
            User user = null;
        Cookie[] cookies = request.getCookies();      //从网页中得到cookies存入到Cookie数组
        for(Cookie cookie:cookies)                    //循环Cookie
        {
            if(cookie.getName().equals("token"))        //判断是否有名字是token的
            {
                String token = cookie.getName();          //如果有定义一个token   来获取这个token的名字
                 user = userMapper.findToken(cookie.getValue()); //调用UserMapper中的findToken()方法来判断数据库中是否有token值和cookie.getValue相同
                if(user != null)
                {
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if(user == null)
        {
            model.addAttribute("error","用户信息错误");
            return "publish";
        }
        Question question = new Question();
        List<Question> numAll = questionMapper.QuestionsearchAll();
        int  num = numAll.size();
        num++;
        question.setId(num);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());

        questionMapper.create(question);

        return "redirect:/index";
    }
}
