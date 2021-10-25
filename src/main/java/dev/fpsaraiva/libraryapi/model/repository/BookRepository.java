package dev.fpsaraiva.libraryapi.model.repository;

import dev.fpsaraiva.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
