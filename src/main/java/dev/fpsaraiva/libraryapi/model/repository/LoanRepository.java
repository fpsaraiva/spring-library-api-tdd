package dev.fpsaraiva.libraryapi.model.repository;

import dev.fpsaraiva.libraryapi.model.entity.Book;
import dev.fpsaraiva.libraryapi.model.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query(value = "SELECT CASE WHEN (COUNT(l.id) > 0) THEN true ELSE false END FROM Loan l WHERE l.book =:book AND (l.returned IS NULL OR l.returned IS FALSE)")
    boolean existsByBookAndNotReturned(@Param("book") Book book);

    @Query(value = "SELECT l FROM Loan as l JOIN l.book as b WHERE b.isbn = :isbn OR l.customer =:customer")
    Page<Loan> findByBookIsbnOrCustomer(
            @Param("isbn") String isbn,
            @Param("customer") String customer,
            Pageable pageable);
}
