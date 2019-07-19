package com.boot.example.demo.Mapper;

import com.boot.example.demo.model.Question;
import com.boot.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

        @Insert("insert into question (id,title,description,creator,gmt_create,gmt_modified,tag)" +
                "values (#{id},#{title},#{description},#{creator},#{gmt_create},#{gmt_modified},#{tag})")
        public void create(Question question);

        @Select("select * from question")
        List<Question> QuestionsearchAll();
}
