package com.boot.example.demo.Controller;


import com.boot.example.demo.Mapper.UserMapper;
import com.boot.example.demo.dto.PageactionDTO;
import com.boot.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.boot.example.demo.model.User;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class profileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/profile/{action}")
    //@PathVariable 用来得到网址后面参数的值  比如说 questions  就可以代表是“我的问题页面”  如果换成我的草稿 就在profile中换成相应的名字 然后在这里面获取就好了
    public String  profile(@PathVariable(name = "action") String action,
                           Model model,
                           HttpServletRequest request,
                           @RequestParam(name = "page",defaultValue = "1") Integer page,
                           @RequestParam(name = "size",defaultValue = "3") Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
        {
            return "redirect:/index";
        }

        if("questions".equals(action))
        {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","最新动态");
        }else if("draft".equals(action))
        {
            model.addAttribute("section","draft");
            model.addAttribute("sectionName","我的草稿");
        }else if("collection".equals(action))
        {
            model.addAttribute("section","collection");
            model.addAttribute("sectionName","我的收藏");
        }else if("follow".equals(action))
        {
            model.addAttribute("section","follow");
            model.addAttribute("sectionName","我关注的话题");
        }else if("reply".equals(action))
        {
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","邀请我回复的消息");
        }
        PageactionDTO pageactionDTO = questionService.listByUserId(user.getId(), page, size);
        model.addAttribute("pageaction",pageactionDTO);
        return "profile";
    }

}
