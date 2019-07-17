package com.memory.gwzz.redis.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Album;
import com.memory.gwzz.redis.service.AlbumRedisMobileService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.redis.CacheConstantConfig.ALBUMVIEW;
import static com.memory.redis.CacheConstantConfig.ALBUMVIEWMANAGER;

/**
 * @ClassName AlbumRedisMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/17 14:19
 */
@Service("albumRedisMobileService")
public class AlbumRedisMobileServiceImpl implements AlbumRedisMobileService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 查询专辑阅读量
     * @param albumId
     * @return
     */
    @Override
    public int getAlbumView(String albumId){
        Integer view = 0;
        Integer albumView = 0;
        Integer albumViewManager = 0 ;
        Object av = null;
        Object avm = null;
        try {
            av = redisUtil.get(ALBUMVIEW + albumId);
            avm = redisUtil.get(ALBUMVIEWMANAGER + albumId);
            if (av==null){
                Album album = (Album) daoUtils.getById("Album",albumId);
                if (album != null){
                    redisUtil.set(ALBUMVIEW + albumId,album.getAlbumTotalView()+"");
                    av = redisUtil.get(ALBUMVIEW + albumId);
                    if (av==null){
                        albumView = album.getAlbumTotalView();
                    }else{
                        albumView = Integer.valueOf(av.toString());
                    }
                }
            }else{
                albumView = Integer.valueOf(av.toString());
            }
            if (avm == null){
                redisUtil.set(ALBUMVIEWMANAGER + albumId,"0");
                albumViewManager = 0;
            }else{
                albumViewManager = Integer.valueOf(avm.toString());
            }
            view = albumView + albumViewManager;
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
}
