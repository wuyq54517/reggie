package com.itheima.controller;

import com.itheima.dto.R;
import com.itheima.dto.StudentQueryDto;
import com.itheima.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:7070")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/api/students")
//    @CrossOrigin("http://localhost:7070")
    public R all() {
        return R.ok(studentService.findAll());
    }

    @GetMapping("/api/students/q")
    public R q(StudentQueryDto queryDto) {
        return R.ok(studentService.findBy(queryDto));
    }
}
