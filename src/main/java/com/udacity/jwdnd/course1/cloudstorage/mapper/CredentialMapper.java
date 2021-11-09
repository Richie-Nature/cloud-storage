package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> findAll(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, username,key,password, userid)" +
            "values(#{url}, #{username},#{key},#{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{id} AND userid = #{userId}")
    Credential findById(Integer id, Integer userId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, " +
            "key = #{key}, password = #{password}\n" +
            "WHERE userid = #{userId} AND credentialid = #{id}")
    int update(String url, String username,String key,
               String password,Integer id, Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id} AND userid = #{userId}")
    int delete(Integer id, Integer userId);
}
