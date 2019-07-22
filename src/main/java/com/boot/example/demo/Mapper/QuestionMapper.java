package com.boot.example.demo.Mapper;

import com.boot.example.demo.model.Question;
import com.boot.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
                                                                                //插入一条数据
        @Insert("insert into question (id,title,description,creator,gmt_create,gmt_modified,tag)" +
                "values (#{id},#{title},#{description},#{creator},#{gmt_create},#{gmt_modified},#{tag})")
        public void create(Question question);

        @Select("select * from question ")                    //查询全部信息
        List<Question> QuestionsearchAll();

        @Select("select * from question limit #{offSize},#{size}")                    //分页查询
        List<Question> QuestionsearchPage(@Param(value = "offSize") Integer offSize, @Param(value = "size") Integer size);


}
