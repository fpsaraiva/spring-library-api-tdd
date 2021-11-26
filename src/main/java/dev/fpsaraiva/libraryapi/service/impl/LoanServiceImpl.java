package dev.fpsaraiva.libraryapi.service.impl;

import dev.fpsaraiva.libraryapi.api.dto.LoanFilterDTO;
import dev.fpsaraiva.libraryapi.exception.BusinessException;
import dev.fpsaraiva.libraryapi.model.entity.Book;
import dev.fpsaraiva.libraryapi.model.entity.Loan;
import dev.fpsaraiva.libraryapi.model.repository.LoanRepository;
import dev.fpsaraiva.libraryapi.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

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

    @Override
    public Optional<Loan> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Loan update(Loan loan) {
        if(loan == null || loan.getId() == null) {
            throw new IllegalArgumentException("Empréstimo não pode ser/possuir ID nulo.");
        }
        return repository.save(loan);
    }

    @Override
    public Page<Loan> find(LoanFilterDTO filter, Pageable pageable) {
        return repository.findByBookIsbnOrCustomer(filter.getIsbn(), filter.getCustomer(), pageable);
    }

    @Override
    public Page<Loan> getLoansByBook(Book book, Pageable pageable) {
        return repository.findByBook(book, pageable);
    }
}
