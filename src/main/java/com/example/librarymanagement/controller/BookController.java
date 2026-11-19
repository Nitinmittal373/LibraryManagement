package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private List<Book> books = new ArrayList<>();

    private int nextId = 1;

    // 1. Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        book.setId(nextId++);

        books.add(book);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(book);
    }

    // 2. Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        return ResponseEntity.ok(books);
    }

    // 3. Get a specific book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @PathVariable int id) {

        for (Book book : books) {

            if (book.getId() == id) {
                return ResponseEntity.ok(book);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // 4. Update book details
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable int id,
            @RequestBody Book updatedBook) {

        for (Book book : books) {

            if (book.getId() == id) {

                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setCategory(updatedBook.getCategory());
                book.setAvailableCopies(
                        updatedBook.getAvailableCopies()
                );

                return ResponseEntity.ok(book);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // 5. Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable int id) {

        boolean removed = books.removeIf(
                book -> book.getId() == id
        );

        if (removed) {
            return ResponseEntity.ok(
                    "Book deleted successfully"
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Book not found");
    }
}