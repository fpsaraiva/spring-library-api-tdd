package dev.fpsaraiva.libraryapi.api.dto;

public class LoanDTO {

    private String isbn;
    private String customer;

    public LoanDTO() {
    }

    public LoanDTO(String isbn, String customer) {
        this.isbn = isbn;
        this.customer = customer;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCustomer() {
        return customer;
    }
}
