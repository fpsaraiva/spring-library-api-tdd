package dev.fpsaraiva.libraryapi.api.resource;

import dev.fpsaraiva.libraryapi.api.dto.BookDTOResponse;
import dev.fpsaraiva.libraryapi.api.dto.LoanDTO;
import dev.fpsaraiva.libraryapi.api.dto.LoanFilterDTO;
import dev.fpsaraiva.libraryapi.api.dto.ReturnedLoanDTO;
import dev.fpsaraiva.libraryapi.model.entity.Book;
import dev.fpsaraiva.libraryapi.model.entity.Loan;
import dev.fpsaraiva.libraryapi.service.BookService;
import dev.fpsaraiva.libraryapi.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService service;

    private final BookService bookService;

    public LoanController(LoanService service, BookService bookService) {
        this.service = service;
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO dto) {
        Book book = bookService.getBookByIsbn(dto.getIsbn())
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST , "Book not found for informed isbn."));
        Loan entity = new Loan(book.getId(), dto.getCustomer(), book, LocalDate.now(), false);

        entity = service.save(entity);

        return entity.getId();
    }

    @PatchMapping("/{id}")
    public void returnBook(@PathVariable Long id, @RequestBody ReturnedLoanDTO dto) {
        Loan loan = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Loan not found for informed ID."));

        loan.setReturned(dto.getReturned());

        service.update(loan);
    }

    @GetMapping
    public Page<LoanDTO> find(LoanFilterDTO dto, Pageable pageRequest) {
        Page<Loan> result = service.find(dto, pageRequest);
        List<LoanDTO> list = result.getContent().stream()
                .map(entity -> {
                        Book book = entity.getBook();
                        BookDTOResponse bookDTO = new BookDTOResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn());
                        LoanDTO loanDTO = new LoanDTO(book.getIsbn(), entity.getCustomer(), bookDTO);
                        return loanDTO;
                }).collect(Collectors.toList());
        return new PageImpl<>(list, pageRequest, result.getTotalElements());
    }
}
