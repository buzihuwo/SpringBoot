package com.zhaolong.lesson4.controller;

import com.zhaolong.lesson4.entity.StudentEntity;
import com.zhaolong.lesson4.jpa.StudentJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private StudentJPA studentJPA;

    @PostMapping("/login")
    public String login(StudentEntity student, HttpServletRequest request) {
        String result = "登录成功";
        Boolean flag = true;
        Optional<StudentEntity> studentEntity = studentJPA.findOne(new Specification<StudentEntity>() {
            @Override
            public Predicate toPredicate(Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.where(criteriaBuilder.equal(root.get("lastName"), student.getLastName()));
                return null;
            }
        });
        if (!studentEntity.isPresent()) {
            flag = false;
            result = "用户不存在";
        } else if (!studentEntity.get().getFirstMidName().equals(student.getFirstMidName())) {
            flag = false;
            return "FirstMidName不同";
        }
        if (flag) {
            request.getSession().setAttribute("session_user", studentEntity);
        }

//        List<StudentEntity> list = studentJPA.findByFirstMidNameAndLastName(firstMidName, fisrtName);

        return result;
    }
}
