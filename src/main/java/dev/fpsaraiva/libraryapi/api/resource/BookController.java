package dev.fpsaraiva.libraryapi.api.resource;

import dev.fpsaraiva.libraryapi.api.dto.BookDTORequest;
import dev.fpsaraiva.libraryapi.api.dto.BookDTOResponse;
import dev.fpsaraiva.libraryapi.api.dto.LoanDTO;
import dev.fpsaraiva.libraryapi.model.entity.Book;
import dev.fpsaraiva.libraryapi.model.entity.Loan;
import dev.fpsaraiva.libraryapi.service.BookService;
import dev.fpsaraiva.libraryapi.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@Api("Book API")
public class BookController {

    private final BookService service;

    private final LoanService loanService;

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService service, LoanService loanService) {
        this.service = service;
        this.loanService = loanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create a book")
    public BookDTOResponse create(@RequestBody @Valid BookDTORequest dto) {
        log.info("Creating a book for ISBN: {} ", dto.getIsbn());
        Book entity = dto.toEntity();
        entity = service.save(entity);
        return new BookDTOResponse(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn());
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtains details of a book from its ID")
    public BookDTOResponse get(@PathVariable Long id) {
        log.info("Obtaining details for book id: {} ", id);
        try {
            Book entity = service.getById(id).get();
            return new BookDTOResponse(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn());
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete a book")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Book deleted with success")
    })
    public void delete(@PathVariable Long id) {
        log.info("Deleting book with id: {} ", id);
        try {
            Book entity = service.getById(id).get();
            service.delete(entity);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a book")
    public BookDTOResponse update(@PathVariable Long id, BookDTORequest dto) {
        log.info("Updating book with id: {} ", id);
        try {
            Book entity = service.getById(id).get();
            entity.setTitle(dto.getTitle());
            entity.setAuthor(dto.getAuthor());
            entity = service.update(entity);
            return new BookDTOResponse(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn());
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @ApiOperation("Find books by params")
    public Page<BookDTOResponse> find(BookDTOResponse dto, Pageable pageable) {
        log.info("Listing all books available.");
        Book filter = new Book(dto.getId(), dto.getTitle(), dto.getAuthor(), dto.getIsbn());
        Page<Book> result = service.find(filter, pageable);
        List<BookDTOResponse> list = result.getContent().stream()
                .map(entity -> new BookDTOResponse(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn()))
                .collect(Collectors.toList());
        return new PageImpl<BookDTOResponse>(list, pageable, result.getTotalElements());
    }

    @GetMapping("/{id}/loans")
    public Page<LoanDTO> loansByBook(@PathVariable Long id, Pageable pageable) {
        Book book = service.getById(id).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        Page<Loan> result = loanService.getLoansByBook(book, pageable);
        List<LoanDTO> list = result.getContent()
                .stream()
                .map(loan -> {
                    Book loanBook = loan.getBook();
                    BookDTOResponse bookDTO = new BookDTOResponse(loanBook.getId(), loanBook.getTitle(), loanBook.getAuthor(), loanBook.getIsbn());
                    return new LoanDTO(loanBook.getIsbn(), loan.getCustomer(), bookDTO);
                })
                .collect(Collectors.toList());
        return new PageImpl<LoanDTO>(list, pageable, result.getTotalElements());
    }
}
