package com.memory.cms.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author INS6+
 * @date 2019/5/10 15:58
 */

public class CourseExtExcel {

    @Excel(name = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String course_ext_nickname;

/*    @Excel(name = "头像")
    @NotBlank(message = "头像不能为空")
    private String course_ext_logo;*/

    @Excel(name ="内容类型")
    @NotBlank(message = "内容类型不能为空")
    private String course_ext_type;

    @Excel(name ="文字")
    private String course_ext_words ;

   // @Excel(name ="图片")
    @Excel(name ="图片")
    private String course_ext_img_url ;

    @Excel(name ="音频")
    private String course_ext_audio ;

    @Excel(name ="音频时长")
    private Integer course_ext_audio_times ;

//    private String course_ext_sort;


    public CourseExtExcel(){

    }

    public CourseExtExcel(String course_ext_nickname, String course_ext_type, String course_ext_words, String course_ext_img_url, String course_ext_audio, int course_ext_audio_times) {
        this.course_ext_nickname = course_ext_nickname;
        this.course_ext_type = course_ext_type;
        this.course_ext_words = course_ext_words;
        this.course_ext_img_url = course_ext_img_url;
        this.course_ext_audio = course_ext_audio;
        this.course_ext_audio_times = course_ext_audio_times;
    }

    public String getCourse_ext_nickname() {
        return course_ext_nickname;
    }

    public void setCourse_ext_nickname(String course_ext_nickname) {
        this.course_ext_nickname = course_ext_nickname;
    }

 /*   public String getCourse_ext_logo() {
        return course_ext_logo;
    }

    public void setCourse_ext_logo(String course_ext_logo) {
        this.course_ext_logo = course_ext_logo;
    }*/

    public String getCourse_ext_type() {
        return course_ext_type;
    }

    public void setCourse_ext_type(String course_ext_type) {
        this.course_ext_type = course_ext_type;
    }

    public String getCourse_ext_words() {
        return course_ext_words;
    }

    public void setCourse_ext_words(String course_ext_words) {
        this.course_ext_words = course_ext_words;
    }

    public String getCourse_ext_img_url() {
        return course_ext_img_url;
    }

    public void setCourse_ext_img_url(String course_ext_img_url) {
        this.course_ext_img_url = course_ext_img_url;
    }

    public String getCourse_ext_audio() {
        return course_ext_audio;
    }

    public void setCourse_ext_audio(String course_ext_audio) {
        this.course_ext_audio = course_ext_audio;
    }

    public Integer getCourse_ext_audio_times() {
        return course_ext_audio_times;
    }

    public void setCourse_ext_audio_times(Integer course_ext_audio_times) {
        this.course_ext_audio_times = course_ext_audio_times;
    }

    @Override
    public String toString() {
        return "CourseExtExcel{" +
                "course_ext_nickname='" + course_ext_nickname + '\'' +
                ", course_ext_type='" + course_ext_type + '\'' +
                ", course_ext_words='" + course_ext_words + '\'' +
                ", course_ext_img_url='" + course_ext_img_url + '\'' +
                ", course_ext_audio='" + course_ext_audio + '\'' +
                ", course_ext_audio_times=" + course_ext_audio_times +
                '}';
    }
}
