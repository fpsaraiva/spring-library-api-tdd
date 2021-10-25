package dev.fpsaraiva.libraryapi.api.resource;

import dev.fpsaraiva.libraryapi.api.dto.BookDTORequest;
import dev.fpsaraiva.libraryapi.api.dto.BookDTOResponse;
import dev.fpsaraiva.libraryapi.model.Book;
import dev.fpsaraiva.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTOResponse create(@RequestBody BookDTORequest dto) {
        Book entity = dto.toEntity();
        entity = service.save(entity);
        return new BookDTOResponse(entity.getId(), entity.getAuthor(), entity.getTitle(), entity.getIsbn());
    }
}
