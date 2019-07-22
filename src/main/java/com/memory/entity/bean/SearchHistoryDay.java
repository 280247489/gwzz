package com.memory.entity.bean;
/**
 * @author INS6+
 * @date 2019/7/22 15:00
 */

public class SearchHistoryDay {

    private String timeHMD;
    private String keyWord;
    private long total;

    public SearchHistoryDay() {
    }

    public SearchHistoryDay(String timeHMD, String keyWord, long total) {
        this.timeHMD = timeHMD;
        this.keyWord = keyWord;
        this.total = total;
    }

    public String getTimeHMD() {
        return timeHMD;
    }

    public void setTimeHMD(String timeHMD) {
        this.timeHMD = timeHMD;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
