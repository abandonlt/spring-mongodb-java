package com.pluto.redis.dao;

import com.pluto.redis.entity.TCountDetail;

public interface TCountDetailDao {

    int insertVisitCount(TCountDetail tCountDetail);
    int getVisitCount();

}