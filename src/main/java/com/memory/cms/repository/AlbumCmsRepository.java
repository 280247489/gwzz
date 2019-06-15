package com.memory.cms.repository;

import com.memory.entity.jpa.Album;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author INS6+
 * @date 2019/6/13 17:30
 */

public interface AlbumCmsRepository extends JpaRepository<Album,String> {
}
