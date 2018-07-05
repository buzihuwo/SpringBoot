package com.zhaolong.lesson4.jpa;

import com.zhaolong.lesson4.base.BaseRepository;
import com.zhaolong.lesson4.entity.GoodEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodJPA extends BaseRepository<GoodEntity, String>, QuerydslPredicateExecutor<GoodEntity> {
}
