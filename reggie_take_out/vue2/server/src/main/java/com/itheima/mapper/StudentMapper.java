package com.itheima.mapper;

import com.itheima.dto.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student")
    List<Student> findAll();

    List<Student> findBy(
            @Param("name") String name,
            @Param("sex") String sex,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            @Param("offset") int offset,
            @Param("limit") int limit
    );
}
