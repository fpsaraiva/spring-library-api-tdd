package dev.fpsaraiva.libraryapi.service.impl;

import dev.fpsaraiva.libraryapi.exception.BusinessException;
import dev.fpsaraiva.libraryapi.model.entity.Loan;
import dev.fpsaraiva.libraryapi.model.repository.LoanRepository;
import dev.fpsaraiva.libraryapi.service.LoanService;

public class LoanServiceImpl implements LoanService {

    private LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        if(repository.existsByBookAndNotReturned(loan.getBook())) {
            throw new BusinessException("Book already loaned.");
        }
        return repository.save(loan);
    }
}
