package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Note;
import com.ocere.portal.repository.NoteRepository;
import com.ocere.portal.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public Optional<Note> findNoteById(int id) {
        return noteRepository.findById(id);
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.saveAndFlush(note);
    }

    @Override
    public void deleteNoteById(int id) throws Exception {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        } else {
            throw new Exception("Couldn't delete note, because it didn't exist!");
        }
    }

    @Override
    public Note updateNote(Note note, int id) throws Exception {
        Note updatedNote;
        Optional<Note> optionalUpdatedNote = findNoteById(id);

        if (optionalUpdatedNote.isPresent()) {
            updatedNote = optionalUpdatedNote.get();
            updatedNote.setTitle(note.getTitle());
            updatedNote.setText(note.getText());
            updatedNote.setCreatedAt(note.getCreatedAt());
            updatedNote.setClient(note.getClient());
            updatedNote.setTicket(note.getTicket());
            updatedNote.setAuthor(note.getAuthor());
        } else {
            throw new Exception("Couldn't update note, because it didn't exist!");
        }

        return noteRepository.saveAndFlush(updatedNote);
    }

    @Override
    public void saveNote(Note note) {
        noteRepository.saveAndFlush(note);
    }
}
