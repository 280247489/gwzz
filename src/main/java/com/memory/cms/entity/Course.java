package com.memory.cms.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/9 13:09
 */

@Entity
public class Course {
    private int id;
    private String typeId;
    private String articleTitle;
    private String articleLogo;
    private String articleContent;
    private String articleAudioUrl;
    private String articleVideoUrl;
    private String articleLabel;
    private String articleKeyWords;
    private int articleOnline;
    private int articleTotalView;
    private int articleTotalShare;
    private int articleTotalLike;
    private Date articleCreateTime;
    private String articleCreateId;
    private Date articleUpdateTime;
    private String articleUpdateId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type_id")
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "article_title")
    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    @Basic
    @Column(name = "article_logo")
    public String getArticleLogo() {
        return articleLogo;
    }

    public void setArticleLogo(String articleLogo) {
        this.articleLogo = articleLogo;
    }

    @Basic
    @Column(name = "article_content")
    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    @Basic
    @Column(name = "article_audio_url")
    public String getArticleAudioUrl() {
        return articleAudioUrl;
    }

    public void setArticleAudioUrl(String articleAudioUrl) {
        this.articleAudioUrl = articleAudioUrl;
    }

    @Basic
    @Column(name = "article_video_url")
    public String getArticleVideoUrl() {
        return articleVideoUrl;
    }

    public void setArticleVideoUrl(String articleVideoUrl) {
        this.articleVideoUrl = articleVideoUrl;
    }

    @Basic
    @Column(name = "article_label")
    public String getArticleLabel() {
        return articleLabel;
    }

    public void setArticleLabel(String articleLabel) {
        this.articleLabel = articleLabel;
    }

    @Basic
    @Column(name = "article_key_words")
    public String getArticleKeyWords() {
        return articleKeyWords;
    }

    public void setArticleKeyWords(String articleKeyWords) {
        this.articleKeyWords = articleKeyWords;
    }

    @Basic
    @Column(name = "article_online")
    public int getArticleOnline() {
        return articleOnline;
    }

    public void setArticleOnline(int articleOnline) {
        this.articleOnline = articleOnline;
    }

    @Basic
    @Column(name = "article_total_view")
    public int getArticleTotalView() {
        return articleTotalView;
    }

    public void setArticleTotalView(int articleTotalView) {
        this.articleTotalView = articleTotalView;
    }

    @Basic
    @Column(name = "article_total_share")
    public int getArticleTotalShare() {
        return articleTotalShare;
    }

    public void setArticleTotalShare(int articleTotalShare) {
        this.articleTotalShare = articleTotalShare;
    }

    @Basic
    @Column(name = "article_total_like")
    public int getArticleTotalLike() {
        return articleTotalLike;
    }

    public void setArticleTotalLike(int articleTotalLike) {
        this.articleTotalLike = articleTotalLike;
    }

    @Basic
    @Column(name = "article_create_time")
    public Date getArticleCreateTime() {
        return articleCreateTime;
    }

    public void setArticleCreateTime(Date articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    @Basic
    @Column(name = "article_create_id")
    public String getArticleCreateId() {
        return articleCreateId;
    }

    public void setArticleCreateId(String articleCreateId) {
        this.articleCreateId = articleCreateId;
    }

    @Basic
    @Column(name = "article_update_time")
    public Date getArticleUpdateTime() {
        return articleUpdateTime;
    }

    public void setArticleUpdateTime(Date articleUpdateTime) {
        this.articleUpdateTime = articleUpdateTime;
    }

    @Basic
    @Column(name = "article_update_id")
    public String getArticleUpdateId() {
        return articleUpdateId;
    }

    public void setArticleUpdateId(String articleUpdateId) {
        this.articleUpdateId = articleUpdateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                articleOnline == course.articleOnline &&
                articleTotalView == course.articleTotalView &&
                articleTotalShare == course.articleTotalShare &&
                articleTotalLike == course.articleTotalLike &&
                Objects.equals(typeId, course.typeId) &&
                Objects.equals(articleTitle, course.articleTitle) &&
                Objects.equals(articleLogo, course.articleLogo) &&
                Objects.equals(articleContent, course.articleContent) &&
                Objects.equals(articleAudioUrl, course.articleAudioUrl) &&
                Objects.equals(articleVideoUrl, course.articleVideoUrl) &&
                Objects.equals(articleLabel, course.articleLabel) &&
                Objects.equals(articleKeyWords, course.articleKeyWords) &&
                Objects.equals(articleCreateTime, course.articleCreateTime) &&
                Objects.equals(articleCreateId, course.articleCreateId) &&
                Objects.equals(articleUpdateTime, course.articleUpdateTime) &&
                Objects.equals(articleUpdateId, course.articleUpdateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeId, articleTitle, articleLogo, articleContent, articleAudioUrl, articleVideoUrl, articleLabel, articleKeyWords, articleOnline, articleTotalView, articleTotalShare, articleTotalLike, articleCreateTime, articleCreateId, articleUpdateTime, articleUpdateId);
    }
}
