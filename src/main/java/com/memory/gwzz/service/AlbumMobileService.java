package com.memory.gwzz.service;

import java.util.Map;

/**
 * @ClassName AlbumMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/18 15:00
 */
public interface AlbumMobileService {
    Map<String,Object> fandAlbum(Integer start,Integer limit);
    Map<String,Object> fandById(String id);
    Map<String,Object> fandCourseByAlbunmId(String albumId,Integer start,Integer limit);
}
