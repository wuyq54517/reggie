package com.itheima.service;

import com.itheima.dto.Student;
import com.itheima.dto.StudentQueryDto;
import com.itheima.mapper.StudentMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    public List<Student> findAll() {
        return studentMapper.findAll();
    }

    public List<Student> findBy(StudentQueryDto q) {
        return studentMapper.findBy(q.name(), q.sex(), q.age(), q.offset(), q.limit());
    }

    public long findCount(StudentQueryDto q) {
        return studentMapper.findCount(q.name(), q.sex(), q.age());
    }

    public void insert(Student student) {
        studentMapper.insert(student);
    }

    public void update(Student student) {
        studentMapper.update(student);
    }

    public void delete(int id) {
        studentMapper.delete(id);
    }

    public Student find(int id) {
        return studentMapper.find(id);
    }

    @Autowired
    private StudentMapper studentMapper;

}
