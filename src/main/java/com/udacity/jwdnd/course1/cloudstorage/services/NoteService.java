package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note, int userId) {
        return noteMapper.insert(new Note(null,
                note.getNotetitle(), note.getNotedescription(), userId));
    }

    public Stream<Note> getAllNotes(int userId) {
        return noteMapper.findAll(userId).stream();
    }

    public Note getNote(int noteId, int userId) {
        return noteMapper.findById(noteId, userId);
    }

    public int updateNote(Note note, int userId) {
        return noteMapper.update(note.getNotetitle(),
                note.getNotedescription(),
                note.getNoteid(), userId);
    }

    public int deleteNote(int noteId, int userId) {
        return noteMapper.delete(noteId, userId);
    }
}
