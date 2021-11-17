package dev.fpsaraiva.libraryapi.service.impl;

import dev.fpsaraiva.libraryapi.exception.BusinessException;
import dev.fpsaraiva.libraryapi.model.Book;
import dev.fpsaraiva.libraryapi.model.repository.BookRepository;
import dev.fpsaraiva.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        if(repository.existsByIsbn(book.getIsbn())) {
            throw new BusinessException("Isbn já cadastrado.");
        }
        return repository.save(book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.empty();
    }
}
