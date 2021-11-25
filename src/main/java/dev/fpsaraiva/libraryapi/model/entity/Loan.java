package dev.fpsaraiva.libraryapi.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String customer;

    @JoinColumn(name = "id_book")
    @ManyToOne
    private Book book;

    private LocalDate loanDate;

    private boolean returned;

    public Loan() {}

    //usado para LoanRepositoryTest
    public Loan(String customer, Book book, LocalDate loanDate, boolean returned) {
        this.customer = customer;
        this.book = book;
        this.loanDate = loanDate;
        this.returned = returned;
    }

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

    public String getCustomer() {
        return customer;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public boolean getReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
