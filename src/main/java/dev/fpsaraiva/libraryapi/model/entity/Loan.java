package dev.fpsaraiva.libraryapi.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer;

    private Book book;

    private LocalDate loanDate;

    private boolean returned;

    public Loan() {}

    public Loan(Long id, String customer, Book book, LocalDate loanDate, boolean returned) {
        this.id = id;
        this.customer = customer;
        this.book = book;
        this.loanDate = loanDate;
        this.returned = returned;
    }

    public Long getId() {
        return id;
    }
}
