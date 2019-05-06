package com.memory.solr;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @Auther: cui.Memory
 * @Date: 2019/4/27 0027 9:12
 * @Description:
 */
@Entity
@Table(name = "test_solr", schema = "gwzz_db", catalog = "")
public class TestSolr {
    private int id;
    private String testTitle;
    private String testInfo;
    private String testContent;
    private Date testTime;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "test_title", nullable = false, length = 255)
    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    @Basic
    @Column(name = "test_info", nullable = false, length = 255)
    public String getTestInfo() {
        return testInfo;
    }

    public void setTestInfo(String testInfo) {
        this.testInfo = testInfo;
    }

    @Basic
    @Column(name = "test_content", nullable = false, length = 255)
    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    @Basic
    @Column(name = "test_time", nullable = false)
    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestSolr testSolr = (TestSolr) o;
        return id == testSolr.id &&
                Objects.equals(testTitle, testSolr.testTitle) &&
                Objects.equals(testInfo, testSolr.testInfo) &&
                Objects.equals(testContent, testSolr.testContent) &&
                Objects.equals(testTime, testSolr.testTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, testTitle, testInfo, testContent, testTime);
    }

    @Override
    public String toString() {
        return "TestSolr{" +
                "id=" + id +
                ", testTitle='" + testTitle + '\'' +
                ", testInfo='" + testInfo + '\'' +
                ", testContent='" + testContent + '\'' +
                ", testTime=" + testTime +
                '}';
    }
}
