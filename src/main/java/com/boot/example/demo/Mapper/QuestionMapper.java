package com.boot.example.demo.Mapper;

import com.boot.example.demo.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
                                                                                //插入一条数据
        @Insert("insert into question (title,description,creator,gmt_create,gmt_modified,tag)" +
                "values (#{title},#{description},#{creator},#{gmt_create},#{gmt_modified},#{tag})")
        public void create(Question question);

        @Select("select * from question ")                    //查询全部信息
        List<Question> QuestionsearchAll();

        @Select("select * from question limit #{offSize},#{size}")                    //分页查询
        List<Question> QuestionsearchPage(@Param(value = "offSize") Integer offSize, @Param(value = "size") Integer size);

        @Select("select * from question where id = #{id}")
        Question getById(@Param(value = "id") Integer id);

        @Select("select * from question where creator = #{userId} limit #{offSize},#{size}")
        List<Question> QuestionsearchIdPage(@Param(value = "userId") Integer userId,@Param(value = "offSize") Integer offSize, @Param(value = "size") Integer size);

        @Select("select count(1) from question where creator = #{userId}")
        Integer countByUserId(@Param(value = "userId")Integer userId);

        @Update("update question set title = #{title},description = #{description},gmt_modified = #{gmt_modified},tag = #{tag} where id = #{id}")
        void update(Question question);

}
