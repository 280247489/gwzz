package com.memory.cms.service;

import com.memory.entity.jpa.Album;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/6/13 17:29
 */

public interface AlbumCmsService {

    Album add(Album album);

    Album update(Album album);

    List<Album> queryAllAlbum();

    void delete(String id);

    Album getAlbumById(String id);

    List<Album> queryAlbumByQueHql(int pageIndex,int limit);

    int queryAlbumByQueHqlCount();


}
