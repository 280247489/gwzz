package com.memory.entity.bean;

/**
 * @author INS6+
 * @date 2019/5/14 16:09
 */

/**
 * jodit富文本编辑器返回数据结构
 */
public class JoditImg {

    private Boolean success = false;

    private String time ;

    private JoditData data;

    public JoditImg() {
    }

    public JoditImg(Boolean success, String time, JoditData data) {
        this.success = success;
        this.time = time;
        this.data = data;
    }


    @Override
    public String toString() {
        return "JoditImg{" +
                "success=" + success +
                ", time='" + time + '\'' +
                ", data=" + data +
                '}';
    }
}
