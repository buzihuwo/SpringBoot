package com.zhaolong.lesson4.jpa;

import com.zhaolong.lesson4.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

public interface StudentJPA extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity>, Serializable {
    List<StudentEntity> findByFirstMidNameAndLastName(String firstMidName,String lastName);
}
