package com.zhaolong.lesson6.jpa;

import com.zhaolong.lesson6.entity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerJPA extends JpaRepository<LoggerEntity,Integer> {
}
