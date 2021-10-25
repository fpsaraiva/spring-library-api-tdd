package dev.fpsaraiva.libraryapi.api.dto;

public class BookDTOResponse {

    private Long id;
    private String title;
    private String author;
    private String isbn;

    public BookDTOResponse(Long id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
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

}
