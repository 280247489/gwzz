package com.memory.gwzz.model;

import java.util.Date;
import java.util.Objects;

/**
 * @ClassName Article
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/15 10:52
 */
public class Article {
    private String id;
    private String typeId;
    private String articleTitle;
    private String articleLogo1;
    private String articleLogo2;
    private String articleLogo3;
    private String articleLabel;
    private int articleOnline;
    private int articleTotalComment;
    private int articleTotalView;
    private Date articleReleaseTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleLogo1() {
        return articleLogo1;
    }

    public void setArticleLogo1(String articleLogo1) {
        this.articleLogo1 = articleLogo1;
    }

    public String getArticleLogo2() {
        return articleLogo2;
    }

    public void setArticleLogo2(String articleLogo2) {
        this.articleLogo2 = articleLogo2;
    }

    public String getArticleLogo3() {
        return articleLogo3;
    }

    public void setArticleLogo3(String articleLogo3) {
        this.articleLogo3 = articleLogo3;
    }

    public String getArticleLabel() {
        return articleLabel;
    }

    public void setArticleLabel(String articleLabel) {
        this.articleLabel = articleLabel;
    }

    public int getArticleOnline() {
        return articleOnline;
    }

    public void setArticleOnline(int articleOnline) {
        this.articleOnline = articleOnline;
    }

    public int getArticleTotalComment() {
        return articleTotalComment;
    }

    public void setArticleTotalComment(int articleTotalComment) {
        this.articleTotalComment = articleTotalComment;
    }

    public int getArticleTotalView() {
        return articleTotalView;
    }

    public void setArticleTotalView(int articleTotalView) {
        this.articleTotalView = articleTotalView;
    }

    public Date getArticleReleaseTime() {
        return articleReleaseTime;
    }

    public void setArticleReleaseTime(Date articleReleaseTime) {
        this.articleReleaseTime = articleReleaseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return articleOnline == article.articleOnline &&
                articleTotalComment == article.articleTotalComment &&
                articleTotalView == article.articleTotalView &&
                Objects.equals(id, article.id) &&
                Objects.equals(typeId, article.typeId) &&
                Objects.equals(articleTitle, article.articleTitle) &&
                Objects.equals(articleLogo1, article.articleLogo1) &&
                Objects.equals(articleLogo2, article.articleLogo2) &&
                Objects.equals(articleLogo3, article.articleLogo3) &&
                Objects.equals(articleLabel, article.articleLabel) &&
                Objects.equals(articleReleaseTime, article.articleReleaseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeId, articleTitle, articleLogo1, articleLogo2, articleLogo3, articleLabel, articleOnline, articleTotalComment, articleTotalView, articleReleaseTime);
    }

    public Article() {
    }

    public Article(String id, String typeId, String articleTitle, String articleLogo1, String articleLogo2, String articleLogo3, String articleLabel, int articleOnline, int articleTotalComment, int articleTotalView, Date articleReleaseTime) {
        this.id = id;
        this.typeId = typeId;
        this.articleTitle = articleTitle;
        this.articleLogo1 = articleLogo1;
        this.articleLogo2 = articleLogo2;
        this.articleLogo3 = articleLogo3;
        this.articleLabel = articleLabel;
        this.articleOnline = articleOnline;
        this.articleTotalComment = articleTotalComment;
        this.articleTotalView = articleTotalView;
        this.articleReleaseTime = articleReleaseTime;
    }
}
