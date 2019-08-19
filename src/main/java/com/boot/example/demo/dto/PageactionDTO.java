package com.boot.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class PageactionDTO  {

        private List<QuestionDTO>   questionDTOS;
        private boolean showPrevious;       //上一页
        private boolean showFirstPage;      //第一页
        private boolean showNextPage;       //下一页
        private boolean showEndPage;        //最后一页

        private Integer page;
        private List<Integer> pages  =  new ArrayList<>();
        private Integer totalPage;

        public List<QuestionDTO> getQuestionDTOS() {
                return questionDTOS;
        }

        public void setQuestionDTOS(List<QuestionDTO> questionDTOS) {
                this.questionDTOS = questionDTOS;
        }

        public boolean isShowPrevious() {
                return showPrevious;
        }

        public void setShowPrevious(boolean showPrevious) {
                this.showPrevious = showPrevious;
        }

        public boolean isShowFirstPage() {
                return showFirstPage;
        }

        public void setShowFirstPage(boolean showFirstPage) {
                this.showFirstPage = showFirstPage;
        }

        public boolean isShowNextPage() {
                return showNextPage;
        }

        public void setShowNextPage(boolean showNextPage) {
                this.showNextPage = showNextPage;
        }

        public boolean isShowEndPage() {
                return showEndPage;
        }

        public void setShowEndPage(boolean showEndPage) {
                this.showEndPage = showEndPage;
        }

        public Integer getPage() {
                return page;
        }

        public void setPage(Integer page) {
                this.page = page;
        }

        public List<Integer> getPages() {
                return pages;
        }

        public void setPages(List<Integer> pages) {
                this.pages = pages;
        }

        public Integer getTotalPage() {
                return totalPage;
        }

        public void setTotalPage(Integer totalPage) {
                this.totalPage = totalPage;
        }

        public void setPageTotal(Integer totalPage, Integer page) {
                this.totalPage = totalPage;
                this.page = page;
                pages.add(page);         // totalpage = 4  page = 4   size = 3
                for(int i=1;i<3;i++)
                {
                        if(page - i > 0)                                 //就是往前插入
                        {
                                pages.add(0,page-i);    //    1 2 3
                        }
                        if(page + i <= totalPage)                       //往后插入
                        {
                                pages.add(page+i);
                        }
                }
                //是否展示上一页
                if(page == 1)                     //当页数是第一页的时候
                {
                        showPrevious = false;    //左按钮不显示  ==false
                }
                else{
                        showPrevious = true;       //右按钮显示 ==true
                }
                //是否展示下一页
                if(page == totalPage)             //当页数是最后一页的时候
                {
                        showNextPage = false;      //右按钮不显示 ==false
                }else{
                        showNextPage=true;     //左按钮显示  ==true
                }
                //是否展示第一页
                if(pages.contains(1)){
                        showFirstPage = false;
                }else{
                        showFirstPage = true;
                }
                //是否展示最后一页
                if(pages.contains(totalPage)){
                        showEndPage=false;
                }else{
                        showEndPage=true;
                }

        }
}
