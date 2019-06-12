package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName UserHelp
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/10 16:15
 */
@Entity
@Table(name = "user_help", schema = "gwzz_db", catalog = "")
public class UserHelp {
    private String id;
    private String helpLogo;
    private String helpTitle;
    private String helpSubtitle;
    private int helpType;
    private String helpContent;
    private Date helpCreateTime;
    private String helpCreateId;
    private Date helpUpdateTime;
    private String helpUpdateId;
    private int helpSort;
    private int useYn;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "help_logo")
    public String getHelpLogo() {
        return helpLogo;
    }

    public void setHelpLogo(String helpLogo) {
        this.helpLogo = helpLogo;
    }

    @Basic
    @Column(name = "help_title")
    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    @Basic
    @Column(name = "help_subtitle")
    public String getHelpSubtitle() {
        return helpSubtitle;
    }

    public void setHelpSubtitle(String helpSubtitle) {
        this.helpSubtitle = helpSubtitle;
    }

    @Basic
    @Column(name = "help_type")
    public int getHelpType() {
        return helpType;
    }

    public void setHelpType(int helpType) {
        this.helpType = helpType;
    }

    @Basic
    @Column(name = "help_content")
    public String getHelpContent() {
        return helpContent;
    }

    public void setHelpContent(String helpContent) {
        this.helpContent = helpContent;
    }

    @Basic
    @Column(name = "help_create_time")
    public Date getHelpCreateTime() {
        return helpCreateTime;
    }

    public void setHelpCreateTime(Date helpCreateTime) {
        this.helpCreateTime = helpCreateTime;
    }

    @Basic
    @Column(name = "help_create_id")
    public String getHelpCreateId() {
        return helpCreateId;
    }

    public void setHelpCreateId(String helpCreateId) {
        this.helpCreateId = helpCreateId;
    }

    @Basic
    @Column(name = "help_update_time")
    public Date getHelpUpdateTime() {
        return helpUpdateTime;
    }

    public void setHelpUpdateTime(Date helpUpdateTime) {
        this.helpUpdateTime = helpUpdateTime;
    }

    @Basic
    @Column(name = "help_update_id")
    public String getHelpUpdateId() {
        return helpUpdateId;
    }

    public void setHelpUpdateId(String helpUpdateId) {
        this.helpUpdateId = helpUpdateId;
    }

    @Basic
    @Column(name = "help_sort")
    public int getHelpSort() {
        return helpSort;
    }

    public void setHelpSort(int helpSort) {
        this.helpSort = helpSort;
    }

    @Basic
    @Column(name = "use_yn")
    public int getUseYn() {
        return useYn;
    }

    public void setUseYn(int useYn) {
        this.useYn = useYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHelp userHelp = (UserHelp) o;
        return helpType == userHelp.helpType &&
                helpSort == userHelp.helpSort &&
                useYn == userHelp.useYn &&
                Objects.equals(id, userHelp.id) &&
                Objects.equals(helpLogo, userHelp.helpLogo) &&
                Objects.equals(helpTitle, userHelp.helpTitle) &&
                Objects.equals(helpSubtitle, userHelp.helpSubtitle) &&
                Objects.equals(helpContent, userHelp.helpContent) &&
                Objects.equals(helpCreateTime, userHelp.helpCreateTime) &&
                Objects.equals(helpCreateId, userHelp.helpCreateId) &&
                Objects.equals(helpUpdateTime, userHelp.helpUpdateTime) &&
                Objects.equals(helpUpdateId, userHelp.helpUpdateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, helpLogo, helpTitle, helpSubtitle, helpType, helpContent, helpCreateTime, helpCreateId, helpUpdateTime, helpUpdateId, helpSort, useYn);
    }
}
