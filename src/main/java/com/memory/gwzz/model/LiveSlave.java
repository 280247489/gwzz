package com.memory.gwzz.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/6/12 14:18
 */

public class LiveSlave {

    private String id;
    private String liveSlaveNickname;
    private String liveSlaveLogo;
    private int liveSlaveType;
    private String liveSlaveWords;
    private String liveSlaveImgurl;
    private String liveSlaveAudio;
    private int liveSlaveAudioTime;
    private int liveSlaveSort;

    public LiveSlave() {
    }

    public LiveSlave(String id, String liveSlaveNickname, String liveSlaveLogo, int liveSlaveType, String liveSlaveWords, String liveSlaveImgurl, String liveSlaveAudio, int liveSlaveAudioTime, int liveSlaveSort) {
        this.id = id;
        this.liveSlaveNickname = liveSlaveNickname;
        this.liveSlaveLogo = liveSlaveLogo;
        this.liveSlaveType = liveSlaveType;
        this.liveSlaveWords = liveSlaveWords;
        this.liveSlaveImgurl = liveSlaveImgurl;
        this.liveSlaveAudio = liveSlaveAudio;
        this.liveSlaveAudioTime = liveSlaveAudioTime;
        this.liveSlaveSort = liveSlaveSort;
    }


    /**
     * 重构数据
     * @param list
     * @return
     */
    public List<Map<String,Object>> refactorData(List<LiveSlave> list){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String,Object> mapObj = null;

        for (LiveSlave liveSlave : list) {
            int type  =liveSlave.getLiveSlaveType();
            mapObj = new HashMap<String, Object>();
            mapObj.put("nickName",liveSlave.getLiveSlaveNickname());
            mapObj.put("logo",liveSlave.getLiveSlaveLogo());
            mapObj.put("type",type);
            //文字
            if(type == 1){
                mapObj.put("words",liveSlave.getLiveSlaveWords());
            //音频
            }else if(type == 2){
                mapObj.put("audio",liveSlave.liveSlaveAudio);
                mapObj.put("audioTime",liveSlave.liveSlaveAudioTime);
            //图片
            }else if(type == 3){
                mapObj.put("img",liveSlave.liveSlaveImgurl);
            }
            resultList.add(mapObj);
        }
        return resultList;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiveSlaveNickname() {
        return liveSlaveNickname;
    }

    public void setLiveSlaveNickname(String liveSlaveNickname) {
        this.liveSlaveNickname = liveSlaveNickname;
    }

    public String getLiveSlaveLogo() {
        return liveSlaveLogo;
    }

    public void setLiveSlaveLogo(String liveSlaveLogo) {
        this.liveSlaveLogo = liveSlaveLogo;
    }

    public int getLiveSlaveType() {
        return liveSlaveType;
    }

    public void setLiveSlaveType(int liveSlaveType) {
        this.liveSlaveType = liveSlaveType;
    }

    public String getLiveSlaveWords() {
        return liveSlaveWords;
    }

    public void setLiveSlaveWords(String liveSlaveWords) {
        this.liveSlaveWords = liveSlaveWords;
    }

    public String getLiveSlaveImgurl() {
        return liveSlaveImgurl;
    }

    public void setLiveSlaveImgurl(String liveSlaveImgurl) {
        this.liveSlaveImgurl = liveSlaveImgurl;
    }

    public String getLiveSlaveAudio() {
        return liveSlaveAudio;
    }

    public void setLiveSlaveAudio(String liveSlaveAudio) {
        this.liveSlaveAudio = liveSlaveAudio;
    }

    public int getLiveSlaveAudioTime() {
        return liveSlaveAudioTime;
    }

    public void setLiveSlaveAudioTime(int liveSlaveAudioTime) {
        this.liveSlaveAudioTime = liveSlaveAudioTime;
    }

    public int getLiveSlaveSort() {
        return liveSlaveSort;
    }

    public void setLiveSlaveSort(int liveSlaveSort) {
        this.liveSlaveSort = liveSlaveSort;
    }



}
