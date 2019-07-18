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

    //获取专辑真实阅读量
    Integer getAlbumRealViewTotalWithList(String albumId);

    //获取专辑的伪阅读量
    Integer getAlbumManagerViewWithList(String albumId);


    //获取专辑总的阅读量(实时计算，动态查询专辑下的课程（关联直播等）),后台进行修改操作的时候同步redis用
    Integer getAlbumAllViewTotal(String albumId);

    //获取专辑总的阅读量(根据redis 值相加),查询数据列表
    Integer getAlbumAllViewTotalByKey(String albumId);




}
