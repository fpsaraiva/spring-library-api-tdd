package dev.fpsaraiva.libraryapi.model.repository;

import dev.fpsaraiva.libraryapi.model.entity.Book;
import dev.fpsaraiva.libraryapi.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByBookAndNotReturned(Book book);
}
