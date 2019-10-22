package com.ocere.portal.controller;

import com.ocere.portal.model.Note;
import com.ocere.portal.service.NoteService;
import com.ocere.portal.service.TicketService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;
    private UserService userService;
    private TicketService ticketService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService, TicketService ticketService) {
        this.noteService = noteService;
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping("create")
    public String loadCreateNoteView(Model model, @RequestParam(name="ticketId") int ticketId) {
        model.addAttribute("siteTitle", "Create Note");
        model.addAttribute("action", "create");
        model.addAttribute("submitText", "Create");
        model.addAttribute("cancelPage", "/tickets/" + ticketId);

        Note note = new Note();
        note.setTicket(ticketService.findTicketById(ticketId));

        model.addAttribute("note", note);
        return "notes_form";
    }

    @GetMapping("edit/{id}")
    public String loadNoteEditView(Model model, @PathVariable int id) {
        Note note = noteService.findNoteById(id).get();

        model.addAttribute("siteTitle", "Edit Note");
        model.addAttribute("action", "save/" + id);
        model.addAttribute("submitText", "Save");
        model.addAttribute("cancelPage", "/tickets/" + note.getTicket().getId());

        model.addAttribute("note", note);
        return "notes_form";
    }

    /*
        ACTIONS
     */

    @PostMapping("/create")
    public String createTicket(@ModelAttribute Note note, Principal principal) {
        int ticketId = note.getTicket().getId();
        note.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        note.setAuthor(this.userService.findByEmail(principal.getName()));

        this.noteService.saveNote(note);
        return "redirect:/tickets/" + ticketId;
    }

    @PostMapping("/save/{id}")
    public String saveTicket(@PathVariable int id, @ModelAttribute Note note) throws Exception {
        mapUneditedValuesToNote(note, id);
        int ticketId = note.getTicket().getId();
        this.noteService.updateNote(note, id);
        return "redirect:/tickets/" + ticketId;
    }

    @PostMapping("/delete/{id}")
    public String deleteTicket(@PathVariable int id) throws Exception {
        int ticketId = noteService.findNoteById(id).get().getTicket().getId();
        this.noteService.deleteNoteById(id);
        return "redirect:/tickets/" + ticketId;
    }

    private void mapUneditedValuesToNote(Note note, int id) {
        Note dbNote = noteService.findNoteById(id).get();
        note.setCreatedAt(dbNote.getCreatedAt());
        note.setAuthor(dbNote.getAuthor());
    }
}
