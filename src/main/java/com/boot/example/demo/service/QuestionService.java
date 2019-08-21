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
    public PageactionDTO list(Integer page, Integer size) { //这个statue是用来判断当没有用户登录的时候来显示全部数据

        PageactionDTO pageactionDTO = new PageactionDTO();//定义一个 pageactionDTO 变量   类型是PageactionDTO
        Integer totalpage;
        Integer totalCount = questionMapper.QuestionsearchAll().size();      //获取数据库中一共多少条数据
        if(totalCount % size == 0){
            totalpage = totalCount / size;
        }else {
            totalpage = totalCount / size + 1;
        }
        if(page < 1){
            page = 1;
        }
        if(page > totalpage){
            page = totalpage;
        }

        pageactionDTO.setPageTotal(totalpage,page);

        Integer offSize = (page-1)*size;                                     //算出起始页
        List<Question> questions = questionMapper.QuestionsearchPage(offSize,size);  //调用QuestionsearchPage()方法去得到Question里面的offSize - size的信息
        List<QuestionDTO> questionDTOList = new ArrayList<>();               //定义一个动态数组  questionDTOList  它的类型是 QuestionDTO
        for(Question question:questions)
        {
            User  user =  userMapper.findByID(question.getCreator());         //用方法findByID找寻找在user表里面的id和表question里的creator相同的数据  返回给user\
            QuestionDTO questionDTO = new QuestionDTO();                     //定义一个变量questionDTO   QuestionDTO类型的
            BeanUtils.copyProperties(question,questionDTO);                  //把  questions中的对象 一个一个的复制到questionDTO里面去
            questionDTO.setUser(user);                                       //把比较得到的user也放入questionDTO
            questionDTOList.add(questionDTO);                                //然后封装到动态数组questionDTOList
        }
                //questionDTO里面含有需要显示的所有数据
        pageactionDTO.setQuestionDTOS(questionDTOList);                      //把动态数组封装到pageactionDTO的setQuestionDTOS()

        return pageactionDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);           //实现从数据库读出数据放入一个Question 变量里面封装
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);           //把  questions中的对象 一个一个的复制到questionDTO里面去
        User  user =  userMapper.findByID(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public PageactionDTO listByUserId(Integer userId, Integer page, Integer size) {

        PageactionDTO pageactionDTO = new PageactionDTO();//定义一个 pageactionDTO 变量   类型是PageactionDTO
        Integer totalpage;
        Integer totalCount = questionMapper.countByUserId(userId);      //获取数据库中一共多少条数据

        if(totalCount % size == 0){
            totalpage = totalCount / size;
        }else{
            totalpage = totalCount / size +1;
        }

        if(page < 1){
            page = 1;
        }
        if(page > totalpage){
            page = totalpage;
        }
        pageactionDTO.setPageTotal(totalpage,page);

        Integer offSize = (page-1)*size;                                     //算出起始页
        List<Question> questions = questionMapper.QuestionsearchIdPage(userId,offSize,size);  //调用QuestionsearchPage()方法去得到Question里面的offSize - size的信息
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

    public void CreateOrUpdate(Question question) {

        if(question.getId() == null)
        {
            //插入
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            questionMapper.create(question);
        }else {
            //更新
            question.setGmt_modified(System.currentTimeMillis());
            questionMapper.update(question);
        }


    }
}
