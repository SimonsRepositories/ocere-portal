package com.ocere.portal.service;

import com.ocere.portal.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    List<Note> findAll();

    void deleteNoteById(int id) throws Exception;

    Optional<Note> findNoteById(int id);

    Note createNote(Note note);

    Note updateNote(Note note, int id) throws Exception;

    void saveNote(Note note);
}
