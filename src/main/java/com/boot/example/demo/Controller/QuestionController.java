package com.boot.example.demo.Controller;

import com.boot.example.demo.Mapper.QuestionMapper;
import com.boot.example.demo.dto.QuestionDTO;
import com.boot.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("questions",questionDTO);
        return "question";
    }
}
