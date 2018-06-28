package com.zhaolong.lessonthree.jpa;

import com.zhaolong.lessonthree.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface StudentJPA extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity>, Serializable {
}
