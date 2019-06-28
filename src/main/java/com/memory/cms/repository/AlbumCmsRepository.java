package com.memory.cms.repository;

import com.memory.entity.jpa.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author INS6+
 * @date 2019/6/13 17:30
 */

public interface AlbumCmsRepository extends JpaRepository<Album,String> {

    Album queryAlbumByAlbumName(String albumName);

    @Query(value="from Album a where a.albumName =?1 AND a.id <> ?2")
    Album queryAlbumByAlbumNameAndId(String albumName,String id);


}
