package com.memory.cms.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/8 10:50
 */

@Entity
@Table(name = "article_ext", schema = "gwzz_db", catalog = "")
public class ArticleExt {
    private int id;
    private int articleId;
    private int articleExtType;
    private String articleExtWords;
    private String articleExtImgUrl;
    private String articleExtAudio;
    private int articleExtAudioTimes;
    private int articleExtSort;
    private Timestamp articleExtCreateTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "article_id")
    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @Basic
    @Column(name = "article_ext_type")
    public int getArticleExtType() {
        return articleExtType;
    }

    public void setArticleExtType(int articleExtType) {
        this.articleExtType = articleExtType;
    }

    @Basic
    @Column(name = "article_ext_words")
    public String getArticleExtWords() {
        return articleExtWords;
    }

    public void setArticleExtWords(String articleExtWords) {
        this.articleExtWords = articleExtWords;
    }

    @Basic
    @Column(name = "article_ext_img_url")
    public String getArticleExtImgUrl() {
        return articleExtImgUrl;
    }

    public void setArticleExtImgUrl(String articleExtImgUrl) {
        this.articleExtImgUrl = articleExtImgUrl;
    }

    @Basic
    @Column(name = "article_ext_audio")
    public String getArticleExtAudio() {
        return articleExtAudio;
    }

    public void setArticleExtAudio(String articleExtAudio) {
        this.articleExtAudio = articleExtAudio;
    }

    @Basic
    @Column(name = "article_ext_audio_times")
    public int getArticleExtAudioTimes() {
        return articleExtAudioTimes;
    }

    public void setArticleExtAudioTimes(int articleExtAudioTimes) {
        this.articleExtAudioTimes = articleExtAudioTimes;
    }

    @Basic
    @Column(name = "article_ext_sort")
    public int getArticleExtSort() {
        return articleExtSort;
    }

    public void setArticleExtSort(int articleExtSort) {
        this.articleExtSort = articleExtSort;
    }

    @Basic
    @Column(name = "article_ext_create_time")
    public Timestamp getArticleExtCreateTime() {
        return articleExtCreateTime;
    }

    public void setArticleExtCreateTime(Timestamp articleExtCreateTime) {
        this.articleExtCreateTime = articleExtCreateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleExt that = (ArticleExt) o;
        return id == that.id &&
                articleId == that.articleId &&
                articleExtType == that.articleExtType &&
                articleExtAudioTimes == that.articleExtAudioTimes &&
                articleExtSort == that.articleExtSort &&
                Objects.equals(articleExtWords, that.articleExtWords) &&
                Objects.equals(articleExtImgUrl, that.articleExtImgUrl) &&
                Objects.equals(articleExtAudio, that.articleExtAudio) &&
                Objects.equals(articleExtCreateTime, that.articleExtCreateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleId, articleExtType, articleExtWords, articleExtImgUrl, articleExtAudio, articleExtAudioTimes, articleExtSort, articleExtCreateTime);
    }
}
