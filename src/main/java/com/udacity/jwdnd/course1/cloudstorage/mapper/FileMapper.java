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

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File findById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File findByName(String filename);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int delete(Integer fileId);
}
