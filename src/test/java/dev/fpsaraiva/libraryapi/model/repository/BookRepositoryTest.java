package dev.fpsaraiva.libraryapi.model.repository;

import dev.fpsaraiva.libraryapi.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado.")
    public void returnTrueWhenIsbnExists() {
        //cenario
        String isbn = "123";
        Book book = createNewBook(isbn);
        entityManager.persist(book);

        //acao
        boolean exists = repository.existsByIsbn(isbn);

        //validacao
        assertThat(exists).isTrue();
    }

    private Book createNewBook(String isbn) {
        return new Book("As Aventuras", "Fulano", isbn);
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um livro na base com isbn informado.")
    public void returnFalseWhenIsbnDoesNotExist() {
        //cenario
        String isbn = "123";

        //acao
        boolean exists = repository.existsByIsbn(isbn);

        //validacao
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve obter livro por id")
    public void findByIdTest() {
        Book book = createNewBook("123");
        entityManager.persist(book);

        Optional<Book> foundBook = repository.findById(book.getId());

        assertThat(foundBook.isPresent()).isTrue();
    }
}
