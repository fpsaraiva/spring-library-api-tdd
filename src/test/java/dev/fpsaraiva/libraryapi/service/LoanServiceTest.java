package dev.fpsaraiva.libraryapi.service;

import dev.fpsaraiva.libraryapi.exception.BusinessException;
import dev.fpsaraiva.libraryapi.model.entity.Book;
import dev.fpsaraiva.libraryapi.model.entity.Loan;
import dev.fpsaraiva.libraryapi.model.repository.LoanRepository;
import dev.fpsaraiva.libraryapi.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LoanServiceTest {

    LoanService service;

    @MockBean
    LoanRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new LoanServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um empréstimo")
    public void saveLoanTest() {

        Book book = new Book(1l, "123");
        Loan savingLoan = new Loan(1l, "Fulano", book, LocalDate.now(), true);

        Loan savedLoan = new Loan(1l, "Fulano", book, LocalDate.now(), true);

        when(repository.existsByBookAndNotReturned(book)).thenReturn(false);
        when(repository.save(savingLoan)).thenReturn(savedLoan);

        Loan loan = service.save(savingLoan);

        assertThat(loan.getId()).isEqualTo(savedLoan.getId());
        assertThat(loan.getBook().getId()).isEqualTo(savedLoan.getBook().getId());
        assertThat(loan.getCustomer()).isEqualTo(savedLoan.getCustomer());
        assertThat(loan.getLoanDate()).isEqualTo(savedLoan.getLoanDate());
    }

    @Test
    @DisplayName("Deve lançar erro de negócio ao salvar um empréstimo com livro já emprestado")
    public void loanedBookSaveTest() {

        Book book = new Book(1l, "123");
        Loan savingLoan = new Loan(1l, "Fulano", book, LocalDate.now(), true);

        when(repository.existsByBookAndNotReturned(book)).thenReturn(true);

        Throwable exception = catchThrowable(() -> service.save(savingLoan));

        assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("Book already loaned.");
        verify(repository, never()).save(savingLoan);
    }

    @Test
    @DisplayName("Deve obter as informações de um empréstimo pelo ID")
    public void getLoanDetailsTest() {

        //cenario
        Loan loan = createLoan();
        Long id = loan.getId();
        when(repository.findById(id)).thenReturn(Optional.of(loan));

        //acao
        Optional<Loan> result = service.getById(id);

        //validacao
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getCustomer()).isEqualTo(loan.getCustomer());
        assertThat(result.get().getBook()).isEqualTo(loan.getBook());
        assertThat(result.get().getLoanDate()).isEqualTo(loan.getLoanDate());

        verify(repository).findById(id);
    }


    @Test
    @DisplayName("Deve retornar vazio ao obter um empréstimo por id quando ele não existe")
    public void loanNotFoundByIdTest() {
        Long id = 1l;

        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Loan> loan = service.getById(id);

        assertThat(loan.isPresent()).isFalse();
    }

    public Loan createLoan() {
        Book book = new Book(1l, "123");
        return new Loan(1l, "Fulano", book, LocalDate.now(), true);
    }
}
