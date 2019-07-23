package com.boot.example.demo.service;

import com.boot.example.demo.Mapper.QuestionMapper;
import com.boot.example.demo.Mapper.UserMapper;
import com.boot.example.demo.dto.PageactionDTO;
import com.boot.example.demo.dto.QuestionDTO;
import com.boot.example.demo.model.Question;
import com.boot.example.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService{

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private  UserMapper userMapper;
    public PageactionDTO list(Integer page, Integer size) {

        PageactionDTO pageactionDTO = new PageactionDTO();                   //定义一个 pageactionDTO 变量   类型是PageactionDTO
        Integer totalCount = questionMapper.QuestionsearchAll().size();      //获取数据库中一共多少条数据
        pageactionDTO.setPageTotal(totalCount,page,size);

        if(page < 1){
            page = 1;
        }
        if(page > pageactionDTO.getTotalPage()){
            page = pageactionDTO.getTotalPage();
        }

        Integer offSize = (page-1)*size;                                     //算出起始页
        List<Question> questions = questionMapper.QuestionsearchPage(offSize,size);  //调用QuestionsearchPage()方法去得到Question里面的offSize - size的信息
        List<QuestionDTO> questionDTOList = new ArrayList<>();               //定义一个动态数组  questionDTOList  它的类型是 QuestionDTO
        for(Question question:questions)
        {
            User user =  userMapper.findByID(question.getCreator());         //用方法findByID找寻找在user表里面的id和表question里的creator相同的数据  返回给user
            QuestionDTO questionDTO = new QuestionDTO();                     //定义一个变量questionDTO   QuestionDTO类型的
            BeanUtils.copyProperties(question,questionDTO);                  //把  questions中的对象 一个一个的复制到questionDTO里面去
            questionDTO.setUser(user);                                       //把比较得到的user也放入questionDTO
            questionDTOList.add(questionDTO);                                //然后封装到动态数组questionDTOList
        }
                //questionDTO里面含有需要显示的所有数据
        pageactionDTO.setQuestionDTOS(questionDTOList);                      //把动态数组封装到pageactionDTO的setQuestionDTOS()

        return pageactionDTO;
    }
}
