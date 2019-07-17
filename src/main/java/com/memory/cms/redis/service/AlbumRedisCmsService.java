package com.memory.cms.redis.service;

/**
 * @author INS6+
 * @date 2019/7/17 13:33
 */

public interface AlbumRedisCmsService {


    //获取专辑真实阅读量
    Integer getAlbumRealViewTotal(String albumId);

    //获取专辑的伪阅读量
    Integer getAlbumManagerViewTotal(String albumId);

    //获取专辑总的阅读量
    Integer getAlbumAllViewTotal(String albumId);

    Integer getAlbumAllViewTotalByKey(String albumId);




}
