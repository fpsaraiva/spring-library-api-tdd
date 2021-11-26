package dev.fpsaraiva.libraryapi.api.dto;

public class LoanDTO {

    private String isbn;
    private String customer;
    private BookDTOResponse book;

    public LoanDTO() {
    }

    public LoanDTO(String isbn, String customer) {
        this.isbn = isbn;
        this.customer = customer;
    }

    //criado para LoanControllerTest
    public LoanDTO(String isbn, String customer, BookDTOResponse book) {
        this.isbn = isbn;
        this.customer = customer;
        this.book = book;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCustomer() {
        return customer;
    }
}
