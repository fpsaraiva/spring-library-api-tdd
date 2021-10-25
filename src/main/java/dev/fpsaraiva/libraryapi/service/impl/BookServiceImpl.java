package dev.fpsaraiva.libraryapi.service.impl;

import dev.fpsaraiva.libraryapi.model.Book;
import dev.fpsaraiva.libraryapi.model.repository.BookRepository;
import dev.fpsaraiva.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}
