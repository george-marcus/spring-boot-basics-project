package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;

import org.apache.ibatis.annotations.*;

@Mapper
public interface NotesMapper {

    @Select("SELECT noteId, noteTitle, noteDescription, userid FROM NOTES WHERE userid = #{userid}")
    public List<Note> getNotesByUserId(Integer userid);

    @Select("SELECT COUNT(1) FROM NOTES WHERE noteTitle = #{noteTitle} AND noteId <> NVL(#{noteId}, -1) AND userid = #{userid}")
    public Boolean hasDuplicateTitle(String noteTitle, Integer noteId, Integer userid);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys=true, keyProperty="noteId")
    public int insert(Note note);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    public int update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    public void delete(Integer noteId);
}