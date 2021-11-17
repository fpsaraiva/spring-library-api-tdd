package dev.fpsaraiva.libraryapi.service;

import dev.fpsaraiva.libraryapi.model.Book;

import java.util.Optional;

public interface BookService {
    Book save(Book any);

    Optional<Book> getById(Long id);

    void delete(Book book);
}
