package dev.fpsaraiva.libraryapi.api.dto;

import dev.fpsaraiva.libraryapi.model.Book;

public class BookDTORequest {

    private Long id;
    private String title;
    private String author;
    private String isbn;

    public BookDTORequest(String author, String title, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public Book toEntity() {
        return new Book(id, author, title, isbn);
    }
}
