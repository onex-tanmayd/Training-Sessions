package org.example.exercise;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private final Book book = new Book("1", "title 1", "author 1");
    private final Map<String, Book> books = new HashMap<>(Map.of(book.getId(), book));
    private final BookRepository bookRepository = new BookRepository() {
        @Override
        public Book findById(String id) { return books.get(id); }
        @Override
        public void save(Book b) { books.put(b.getId(), b); }
        @Override
        public List<Book> findAllAvailableBooks() {
            return books.values().stream().filter(Book::isAvailable).toList();
        }
    };
    private final BookService bookService = new BookService(bookRepository);

    @Test
    void borrowBook_success() {
        book.setAvailable(true);
        Book borrowed = bookService.borrowBook("1");
        assertFalse(borrowed.isAvailable());
    }

    @Test
    void borrowBook_notFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> bookService.borrowBook("999"));
        assertEquals("Book not found", ex.getMessage());
    }

    @Test
    void borrowBook_alreadyBorrowed() {
        book.setAvailable(false);
        Exception ex = assertThrows(IllegalStateException.class, () -> bookService.borrowBook("1"));
        assertEquals("Book is already borrowed", ex.getMessage());
    }

    @Test
    void returnBook_success() {
        book.setAvailable(false);
        Book returned = bookService.returnBook("1");
        assertTrue(returned.isAvailable());
    }

    @Test
    void returnBook_notFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> bookService.returnBook("999"));
        assertEquals("Book not found", ex.getMessage());
    }

    @Test
    void returnBook_notBorrowed() {
        book.setAvailable(true);
        Exception ex = assertThrows(IllegalStateException.class, () -> bookService.returnBook("1"));
        assertEquals("Book was not borrowed", ex.getMessage());
    }

    @Test
    void findAvailableBooksByAuthor_returnsCorrectBooks() {
        book.setAvailable(true);
        List<Book> books = bookService.findAvailableBooksByAuthor("author 1");
        assertEquals(1, books.size());
        assertEquals("title 1", books.get(0).getTitle());
    }
}