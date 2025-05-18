package com.notesphere.controller;

import com.notesphere.model.Note;
import com.notesphere.model.Category;
import com.notesphere.model.Visibility;
import com.notesphere.repository.NoteRepository;
import com.notesphere.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        if (categoryId != null) {
            Optional<Category> category = categoryRepository.findById(categoryId);
            if (category.isPresent()) {
                return ResponseEntity.ok(noteRepository.findAllByCategory(category.get()));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok(noteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        if (!noteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        noteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<Note> uploadNote(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("visibility") String visibility,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setVisibility(Visibility.fromString(visibility));
        note.setCategory(categoryRepository.findById(categoryId).orElse(null));

        // dont forget: save document operation can be here

        Note saved = noteRepository.save(note);
        return ResponseEntity.ok(saved);
    }
}
