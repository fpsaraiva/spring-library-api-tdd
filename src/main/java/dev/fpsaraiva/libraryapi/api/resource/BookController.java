package dev.fpsaraiva.libraryapi.api.resource;

import dev.fpsaraiva.libraryapi.api.dto.BookDTORequest;
import dev.fpsaraiva.libraryapi.api.dto.BookDTOResponse;
import dev.fpsaraiva.libraryapi.api.exceptions.ApiErrors;
import dev.fpsaraiva.libraryapi.exception.BusinessException;
import dev.fpsaraiva.libraryapi.model.Book;
import dev.fpsaraiva.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTOResponse create(@RequestBody @Valid BookDTORequest dto) {
        Book entity = dto.toEntity();
        entity = service.save(entity);
        return new BookDTOResponse(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn());
    }

    @GetMapping("/{id}")
    public BookDTOResponse get(@PathVariable Long id) {
        try {
            Book entity = service.getById(id).get();
            return new BookDTOResponse(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn());
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            Book entity = service.getById(id).get();
            service.delete(entity);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException ex) {
        return new ApiErrors(ex);
    }
}
