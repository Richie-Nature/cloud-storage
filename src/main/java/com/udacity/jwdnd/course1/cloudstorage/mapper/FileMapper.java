package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> findAll(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            "values(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    File findById(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userId}")
    File findByName(String filename, Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    int delete(Integer fileId, Integer userId);
}
