package dev.fpsaraiva.libraryapi.api.dto;

public class LoanFilterDTO {

    private Long id;
    private String isbn;
    private String customer;
    private BookDTOResponse book;

    public LoanFilterDTO() {
    }

    //Usado em LoanServiceTest
    public LoanFilterDTO(String isbn, String customer) {
        this.isbn = isbn;
        this.customer = customer;
    }

    public LoanFilterDTO(Long id, String isbn, String customer, BookDTOResponse book) {
        this.id = id;
        this.isbn = isbn;
        this.customer = customer;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCustomer() {
        return customer;
    }

    public BookDTOResponse getBook() {
        return book;
    }
}
