package com.pluto.redis.service;

import javax.annotation.Resource;

import com.pluto.redis.dao.TCountDetailDao;
import com.pluto.redis.entity.TCountDetail;
import com.pluto.redis.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class VisitServiceImpl implements VisitService {
    private static Logger logger = LoggerFactory.getLogger("");
    @Resource
    private TCountDetailDao countDetailDao;

    @Resource
    private RedisUtil cs;

    @Override
    public Long addTCountDetail(TCountDetail detail, String key) {
        //先插入数据库
        countDetailDao.insertVisitCount(detail);
        logger.info("insert to db ====================:" + detail.getId());
        //判断redis中是否有此键（如过期的话，无此键）
        if (cs.existsKey(key)) {
            //若没过期，加1
            return cs.increteData(key, 1L);
        }
        return 0L;
    }

    //获取访问记录
    @Override
    public int getVisitNum(String key) {
        //判断redis有无此键
        if (cs.existsKey(key)) {
            //存在此键的话，从redis中获取缓存记录
            return Integer.valueOf(cs.getDataFromCache(key).toString());
        } else {
            //不存在此键，刚从数据库查出dbCount记录总数
            int dbCount = countDetailDao.getVisitCount();
            //将查出的记录数放到redis缓存中，下次访问直接命中缓存
            cs.putDataToCache(key, String.valueOf(dbCount), 60L); //20秒后过期
            return dbCount;
        }
    }
}
