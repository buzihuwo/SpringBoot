package com.zhaolong.lesson3.jpa;

import com.zhaolong.lesson3.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface StudentJPA extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity>, Serializable {
}
