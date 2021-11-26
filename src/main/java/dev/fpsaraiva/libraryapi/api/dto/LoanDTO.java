package dev.fpsaraiva.libraryapi.api.dto;

import javax.validation.constraints.NotBlank;

public class LoanDTO {

    @NotBlank
    private String isbn;

    @NotBlank
    private String customer;

    @NotBlank
    private String email;
    private BookDTOResponse book;

    public LoanDTO() {
    }

    public LoanDTO(String isbn, String customer) {
        this.isbn = isbn;
        this.customer = customer;
    }

    public LoanDTO(String isbn, String customer, String email) {
        this.isbn = isbn;
        this.customer = customer;
        this.email = email;
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

    public String getEmail() {
        return email;
    }
}
