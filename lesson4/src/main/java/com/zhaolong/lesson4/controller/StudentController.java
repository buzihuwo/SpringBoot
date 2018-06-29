package com.zhaolong.lesson4.controller;

import com.zhaolong.lesson4.entity.StudentEntity;
import com.zhaolong.lesson4.jpa.StudentJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentJPA studentJPA;

    @GetMapping("/list")
    public List<StudentEntity> list() {
        return studentJPA.findAll();
    }

    @GetMapping("/save")
    public StudentEntity save(StudentEntity student) {
        return studentJPA.save(student);
    }

    @GetMapping("/delete")
    public List<StudentEntity> delete(int id) {
        studentJPA.deleteById(id);
        return studentJPA.findAll();
    }

//    @GetMapping("/dataByName")
//    public StudentEntity dataByName(String id) {
//        Queue queue=
//        return  studentJPA.findAll();
//    }
}
