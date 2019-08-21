package com.boot.example.demo.Controller;

import com.boot.example.demo.Mapper.QuestionMapper;
import com.boot.example.demo.Mapper.UserMapper;
import com.boot.example.demo.dto.QuestionDTO;
import com.boot.example.demo.model.Question;
import com.boot.example.demo.model.User;
import com.boot.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired                   //用来给指定的字段或方法注入所需的外部资源
    private QuestionService questionService;

      /*
      1.当点击编辑按钮通过传递过来的id来从数据库里面获取到对应的数据显示在页面上
       */
      @RequestMapping("/publish/{id}")
      public  String edit(@PathVariable(name = "id") Integer id,Model model){
          QuestionDTO question = question = questionService.getById(id);
          model.addAttribute("title",question.getTitle());
          model.addAttribute("description",question.getDescription());
          model.addAttribute("tag",question.getTag());
          model.addAttribute("id",question.getId());
        return "publish";
      }


    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }
    @PostMapping("/publish")
    public String dopublish(
            @RequestParam(value = "title",required = false) String title,     //用RequestParam来得到网页传过来的title的值 放入定义的title中
            @RequestParam(value = "description",required = false) String description,   //和request.getParameter("title");一样的 得到数据
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id" ,required = false) Integer id,
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

        User  user = (User) request.getSession().getAttribute("user");

        if(user == null)
        {
            model.addAttribute("error","用户信息错误");
            return "publish";
        }
        Question question = new Question();

        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        questionService.CreateOrUpdate(question);

        return "redirect:/index";
    }
}
