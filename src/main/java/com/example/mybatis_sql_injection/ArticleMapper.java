package com.example.mybatis_sql_injection;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("SELECT * FROM ARTICLES WHERE ${columnName} = #{columnValue}")
    List<Article> select(@Param("columnName") String columnName, @Param("columnValue") String columnValue);
}
