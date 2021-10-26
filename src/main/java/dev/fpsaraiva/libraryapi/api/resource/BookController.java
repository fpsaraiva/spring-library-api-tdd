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

import javax.validation.Valid;

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
        return new BookDTOResponse(entity.getId(), entity.getAuthor(), entity.getTitle(), entity.getIsbn());
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
