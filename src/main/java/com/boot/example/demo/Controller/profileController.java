package com.boot.example.demo.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class profileController {

    @GetMapping("/profile/{action}")
    //@PathVariable 用来得到网址后面参数的值  比如说 questions  就可以代表是“我的问题页面”  如果换成我的草稿 就在profile中换成相应的名字 然后在这里面获取就好了
    public String  profile(@PathVariable(name = "action") String action,
                           Model model){
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



        return "profile";
    }

}
