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
            @RequestParam(value = "title",required = false) String title,     //用RequestParam来得到网页传过来的title的值 放入定义的title中
            @RequestParam(value = "description",required = false) String description,   //和request.getParameter("title");一样的 得到数据
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
       /*    User user = null;*/
            if(title == null ||title =="")
            {
                model.addAttribute("error","标题不能为空");
                return "publish";
            }
        if(description == null ||description =="")
        {
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag == null ||tag =="")
        {
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

     /* Cookie[] cookies = request.getCookies();//从网页中得到cookies存入到Cookie数组
        if(cookies != null && cookies.length != 0)
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
        }*/

        User  user = (User) request.getSession().getAttribute("user");

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
