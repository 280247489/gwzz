package com.memory.entity.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName Article
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/18 10:14
 */
@Entity
public class Article {
    private String id;
    private String typeId;
    private String articleTitle;
    private String articleLogo1;
    private String articleLogo2;
    private String articleLogo3;
    private String articleContent;
    private String articleAudioUrl;
    private String articleVideoUrl;
    private String articleLabel;
    private String articleKeyWords;
    private int articleOnline;
    private int articleTotalView;
    private int articleTotalShare;
    private int articleTotalLike;
    private int articleTotalComment;
    private Date articleReleaseTime;
    private Date articleCreateTime;
    private String articleCreateId;
    private Date articleUpdateTime;
    private String articleUpdateId;
    private int articleRecommend;
    private String articleDescribe;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    @Column(name = "article_logo1")
    public String getArticleLogo1() {
        return articleLogo1;
    }

    public void setArticleLogo1(String articleLogo1) {
        this.articleLogo1 = articleLogo1;
    }

    @Basic
    @Column(name = "article_logo2")
    public String getArticleLogo2() {
        return articleLogo2;
    }

    public void setArticleLogo2(String articleLogo2) {
        this.articleLogo2 = articleLogo2;
    }

    @Basic
    @Column(name = "article_logo3")
    public String getArticleLogo3() {
        return articleLogo3;
    }

    public void setArticleLogo3(String articleLogo3) {
        this.articleLogo3 = articleLogo3;
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
    @Column(name = "article_total_comment")
    public int getArticleTotalComment() {
        return articleTotalComment;
    }

    public void setArticleTotalComment(int articleTotalComment) {
        this.articleTotalComment = articleTotalComment;
    }

    @Basic
    @Column(name = "article_release_time")
    public Date getArticleReleaseTime() {
        return articleReleaseTime;
    }

    public void setArticleReleaseTime(Date articleReleaseTime) {
        this.articleReleaseTime = articleReleaseTime;
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

    @Basic
    @Column(name = "article_recommend")
    public int getArticleRecommend() {
        return articleRecommend;
    }

    public void setArticleRecommend(int articleRecommend) {
        this.articleRecommend = articleRecommend;
    }

    @Basic
    @Column(name = "article_describe")
    public String getArticleDescribe() {
        return articleDescribe;
    }

    public void setArticleDescribe(String articleDescribe) {
        this.articleDescribe = articleDescribe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return articleOnline == article.articleOnline &&
                articleTotalView == article.articleTotalView &&
                articleTotalShare == article.articleTotalShare &&
                articleTotalLike == article.articleTotalLike &&
                articleTotalComment == article.articleTotalComment &&
                articleRecommend == article.articleRecommend &&
                Objects.equals(id, article.id) &&
                Objects.equals(typeId, article.typeId) &&
                Objects.equals(articleTitle, article.articleTitle) &&
                Objects.equals(articleLogo1, article.articleLogo1) &&
                Objects.equals(articleLogo2, article.articleLogo2) &&
                Objects.equals(articleLogo3, article.articleLogo3) &&
                Objects.equals(articleContent, article.articleContent) &&
                Objects.equals(articleAudioUrl, article.articleAudioUrl) &&
                Objects.equals(articleVideoUrl, article.articleVideoUrl) &&
                Objects.equals(articleLabel, article.articleLabel) &&
                Objects.equals(articleKeyWords, article.articleKeyWords) &&
                Objects.equals(articleReleaseTime, article.articleReleaseTime) &&
                Objects.equals(articleCreateTime, article.articleCreateTime) &&
                Objects.equals(articleCreateId, article.articleCreateId) &&
                Objects.equals(articleUpdateTime, article.articleUpdateTime) &&
                Objects.equals(articleUpdateId, article.articleUpdateId) &&
                Objects.equals(articleDescribe, article.articleDescribe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeId, articleTitle, articleLogo1, articleLogo2, articleLogo3, articleContent, articleAudioUrl, articleVideoUrl, articleLabel, articleKeyWords, articleOnline, articleTotalView, articleTotalShare, articleTotalLike, articleTotalComment, articleReleaseTime, articleCreateTime, articleCreateId, articleUpdateTime, articleUpdateId, articleRecommend, articleDescribe);
    }
}
