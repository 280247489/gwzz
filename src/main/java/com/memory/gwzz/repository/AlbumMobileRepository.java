package com.memory.gwzz.repository;

import com.memory.entity.jpa.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName AlbumMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/20 10:37
 */
public interface AlbumMobileRepository extends JpaRepository<Album,String> {
}
