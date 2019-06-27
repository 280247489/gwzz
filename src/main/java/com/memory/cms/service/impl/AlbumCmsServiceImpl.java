package com.memory.cms.service.impl;

import com.memory.cms.repository.AlbumCmsRepository;
import com.memory.cms.service.AlbumCmsService;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/6/13 17:29
 */
@Service
public class AlbumCmsServiceImpl implements AlbumCmsService {

    @Autowired
    private AlbumCmsRepository repository;

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public Album add(Album album) {
        return repository.save(album);
    }

    @Override
    public Album update(Album album) {
        return repository.save(album);
    }

    @Override
    public List<Album> queryAllAlbum() {
        Sort sort =new Sort(Sort.Direction.DESC,"albumCreateTime","albumUpdateTime");
        return repository.findAll(sort);
    }

    @Override
    public void delete(String id) {
         repository.deleteById(id);
    }

    @Override
    public Album getAlbumById(String id) {
        if(repository.findById(id).hashCode() != 0){
            return repository.findById(id).get();
        }else{
            return null;
        }

    }

    @Override
    public List<Album> queryAlbumByQueHql(int pageIndex, int limit) {
        List<Album> list = new ArrayList<Album>();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" FROM Album  where 1=1 order by  albumIsOnline DESC,albumSort ASC,albumUpdateTime DESC ");
        DaoUtils.Page page = daoUtils.getPage(pageIndex, limit);
        list = daoUtils.findByHQL(stringBuffer.toString(),null,page);
        return list;
    }

    @Override
    public int queryAlbumByQueHqlCount() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select count(*) FROM Album  where 1=1 ");
        return daoUtils.getTotalByHQL(stringBuffer.toString(),null);
    }

    @Override
    public Album queryAlbumByAlbumName(String albumName) {
        return repository.queryAlbumByAlbumName(albumName);
    }

    @Override
    public Album queryAlbumByAlbumNameAndId(String albumName, String id) {
        return repository.queryAlbumByAlbumNameAndId(albumName, id);
    }
}
