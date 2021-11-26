package dev.fpsaraiva.libraryapi.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Loan> loans;

    //construtor usado em teste do método DELETE, classe BookServiceTest
    @Deprecated
    public Book() {}

    public Book(String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    //construtor secundário, utilizado para testes
    public Book(Long id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    //construtor utilizado para testes de emprésitmos
    public Book(Long id, String isbn) {
        this.id = id;
        this.isbn = isbn;
    }

    //setter utilizado no BookServiceTest
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    //setters utilizado no método UPDATE do BookController
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }
}
