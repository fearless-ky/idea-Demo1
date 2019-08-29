package com.boot.example.demo.Util;

import com.boot.example.demo.model.Question;
import org.apache.ibatis.jdbc.SQL;

public class Util  {

    public  String updateView(Question question){

        SQL sql = new SQL();
        sql.UPDATE("question");
        if(question.getTitle() != null){
            sql.SET("title = #{title}");
        }
        if(question.getDescription() != null){
            sql.SET("description = #{description}");
        }
        if(question.getGmt_create() != null){
            sql.SET("gmt_create = #{gmt_create}");
        }
        if(question.getComment_count() != null){
            sql.SET("comment_count = #{comment_count}");
        }
        if(question.getView_count() != null){
            sql.SET("view_count = #{view_count} + view_count");
        }
        if(question.getLike_count() != null){
            sql.SET("like_count = #{like_count}");
        }
        if(question.getTag() != null){
            sql.SET("tag = #{tag}");
        }
        sql.WHERE("id = #{id}");

        return sql.toString();

    }

    public static void main(String[] args) {

        Util ii = new Util();
        Question question = new Question();
        question.setDescription("ste");
        question.setView_count(100);
        question.setComment_count(100);


        System.out.println("vountview = \n" + ii.updateView(question));
    }
}
