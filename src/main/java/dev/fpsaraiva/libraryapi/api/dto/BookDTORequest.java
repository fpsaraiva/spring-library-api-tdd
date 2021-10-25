package dev.fpsaraiva.libraryapi.api.dto;

import dev.fpsaraiva.libraryapi.model.Book;

import javax.validation.constraints.NotBlank;

public class BookDTORequest {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    public BookDTORequest() {
    }

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
