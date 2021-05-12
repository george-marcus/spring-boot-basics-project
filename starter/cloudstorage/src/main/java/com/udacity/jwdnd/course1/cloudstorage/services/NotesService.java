package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesService {

    @Autowired
    private NotesMapper notesMapper;

    @Autowired
    private UserService userService;


    public boolean isDuplicate(Note note, String username) {

        if(note.getNoteTitle() == null || note.getNoteTitle().isBlank()) {
            return false;
        }

        int userid = userService.getUser(username).getUserid();

        return notesMapper.hasDuplicateTitle(note.getNoteTitle(), note.getNoteId(), userid);
    }

    public void saveNote(final Note note, final String username) throws IOException {

        int userid = userService.getUser(username).getUserid();

        note.setUserid(userid);

        int noteId = 0;
        if(note.getNoteId() == null) {
            noteId = notesMapper.insert(note);
        }
        else {
            noteId = notesMapper.update(note);
        }

        if(noteId < 1) {
            throw new IOException("Failed to insert/update Note");
        }
    }

    public void deleteNote(final Integer noteId) {
        notesMapper.delete(noteId);
    }

    public List<Note> getNotesByUser(String userName) {
        int userid = userService.getUser(userName).getUserid();
        return notesMapper.getNotesByUserId(userid);
    }
}
