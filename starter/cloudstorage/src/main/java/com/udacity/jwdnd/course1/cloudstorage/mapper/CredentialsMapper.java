package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT credentialId, url, username, key, password, userid FROM CREDENTIALS WHERE userid = #{userid}")
    public List<Credential> getCredentialsByUserId(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys=true, keyProperty="credentialId")
    public int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialId = #{credentialId} AND userid = #{userid}")
    public int update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId} AND userid = #{userid}")
    public void delete(Integer credentialId, Integer userid);

    @Select("SELECT COUNT(1) FROM CREDENTIALS WHERE url = #{url} AND username = #{username} AND credentialId <> #{credentialId} AND userid = #{userid}")
    public Boolean hasDuplicateUrl(String url, String username, Integer credentialId, Integer userid);

}