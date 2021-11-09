package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> findAll(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)" +
            "values(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId} AND userid = #{userId}")
    Note findById(Integer noteId, Integer userId);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{desc}\n" +
            "WHERE userid = #{userId} AND noteid = #{id}")
    int update(String title, String desc, Integer id, Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId} AND userid = #{userId}")
    int delete(Integer noteId, Integer userId);
}
