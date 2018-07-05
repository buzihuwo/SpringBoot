package com.zhaolong.lesson4.jpa;

import com.zhaolong.lesson4.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional//添加事务   如果使用@Query做改删的话需要添加事务不然会报错
public interface StudentJPA extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity>, Serializable {
    List<StudentEntity> findByFirstMidNameAndLastName(String firstMidName,String lastName);

    @Query(value = "SELECT * from  Student where id>?1",nativeQuery = true)
    public List<StudentEntity> getIdQuery(int id);

    @Modifying
    @Query(value = "DELETE Student where id=?1",nativeQuery = true)
    public void deleteQuery(int id);
}
