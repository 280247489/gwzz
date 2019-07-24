package com.memory.redis.pojo;

import java.util.Date;

/**
 * @author INS6+
 * @date 2019/7/24 11:28
 */

public class SimpleMessage {
    private String publisher;
    private String content;
    private Date createTime;

    public SimpleMessage() {
    }

    public SimpleMessage(String publisher, String content, Date createTime) {
        this.publisher = publisher;
        this.content = content;
        this.createTime = createTime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
