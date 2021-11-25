package dev.fpsaraiva.libraryapi.api.resource;

import dev.fpsaraiva.libraryapi.api.dto.BookDTORequest;
import dev.fpsaraiva.libraryapi.api.dto.BookDTOResponse;
import dev.fpsaraiva.libraryapi.model.entity.Book;
import dev.fpsaraiva.libraryapi.service.BookService;
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

    @PutMapping("/{id}")
    public BookDTOResponse update(@PathVariable Long id, BookDTORequest dto) {
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
    public Page<BookDTOResponse> find(BookDTOResponse dto, Pageable pageRequest) {
        Book filter = new Book(dto.getId(), dto.getTitle(), dto.getAuthor(), dto.getIsbn());
        Page<Book> result = service.find(filter, pageRequest);
        List<BookDTOResponse> list = result.getContent().stream()
                .map(entity -> new BookDTOResponse(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn()))
                .collect(Collectors.toList());
        return new PageImpl<BookDTOResponse>(list, pageRequest, result.getTotalElements());
    }

}
