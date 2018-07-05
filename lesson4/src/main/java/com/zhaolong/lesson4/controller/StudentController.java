package com.zhaolong.lesson4.controller;

import com.zhaolong.lesson4.entity.StudentEntity;
import com.zhaolong.lesson4.jpa.StudentJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/dataByFirstMidNameAndLastName")
    public List<StudentEntity> dataByFirstMidNameAndLastName(String firstMidName, String lastName) {
        return studentJPA.findByFirstMidNameAndLastName(firstMidName, lastName);
    }

    @GetMapping("/getIdQuery")
    public List<StudentEntity> getIdQuery(int id) {
        return studentJPA.getIdQuery(id);
    }

    @GetMapping("/deleteQuery")
    public String deleteQuery(int id) {
        studentJPA.deleteQuery(id);
        return "删除成功";
    }

    @GetMapping("/cutPage")
    public List<StudentEntity> cutPage(int page) {
        StudentEntity student = new StudentEntity();
        student.setSize(2);
        student.setPage(page);
        student.setSord("desc");
        //获取排序对象
        Sort.Direction sort_direction = Sort.Direction.ASC.toString().equalsIgnoreCase(student.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        //设置排序对象参数
        Sort sort = new Sort(sort_direction, student.getSidx());
        //创建分页对象
        PageRequest pageable = PageRequest.of(student.getPage() - 1, student.getSize(), sort);
        //执行分页查询
        return studentJPA.findAll(pageable).getContent();
    }

}
