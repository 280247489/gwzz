package com.memory.entity.bean;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author INS6+
 * @date 2019/6/14 8:58
 */

public class Album {
    private String id;
    private String album_name;
    private String album_logo;
    private Integer album_is_online;
    private Integer album_is_end;
    private Integer album_is_charge;
    private String operator_id;
    private MultipartFile logoFile;
    private String album_synopsis;
    private Integer album_course_limit;
    private Integer album_is_home_page;
    private Integer album_sort;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_logo() {
        return album_logo;
    }

    public void setAlbum_logo(String album_logo) {
        this.album_logo = album_logo;
    }

    public Integer getAlbum_is_online() {
        return album_is_online;
    }

    public void setAlbum_is_online(Integer album_is_online) {
        this.album_is_online = album_is_online;
    }

    public Integer getAlbum_is_end() {
        return album_is_end;
    }

    public void setAlbum_is_end(Integer album_is_end) {
        this.album_is_end = album_is_end;
    }

    public Integer getAlbum_is_charge() {
        return album_is_charge;
    }

    public void setAlbum_is_charge(Integer album_is_charge) {
        this.album_is_charge = album_is_charge;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public MultipartFile getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(MultipartFile logoFile) {
        this.logoFile = logoFile;
    }

    public String getAlbum_synopsis() {
        return album_synopsis;
    }

    public void setAlbum_synopsis(String album_synopsis) {
        this.album_synopsis = album_synopsis;
    }

    public Integer getAlbum_course_limit() {
        return album_course_limit;
    }

    public void setAlbum_course_limit(Integer album_course_limit) {
        this.album_course_limit = album_course_limit;
    }

    public Integer getAlbum_is_home_page() {
        return album_is_home_page;
    }

    public void setAlbum_is_home_page(Integer album_is_home_page) {
        this.album_is_home_page = album_is_home_page;
    }

    public Integer getAlbum_sort() {
        return album_sort;
    }

    public void setAlbum_sort(Integer album_sort) {
        this.album_sort = album_sort;
    }
}

