package dev.fpsaraiva.libraryapi.api.dto;

public class ReturnedLoanDTO {

    private boolean returned;

    public ReturnedLoanDTO() {
    }

    public ReturnedLoanDTO(boolean returned) {
        this.returned = returned;
    }

    public boolean getReturned() {
        return returned;
    }
}
