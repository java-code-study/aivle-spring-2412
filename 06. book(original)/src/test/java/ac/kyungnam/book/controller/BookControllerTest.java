package ac.kyungnam.book.controller;

import ac.kyungnam.book.domain.Book;
import ac.kyungnam.book.dto.BookDTO;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class BookControllerTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        baseUrl = "http://localhost:" + port + "/books";
    }

    @Test
    @Order(1)
    void shouldCreateBookSuccessfully() {
        BookDTO.Post postDto = new BookDTO.Post("New Title", "New SubTitle", "New Author", "New Publisher");
        ResponseEntity<Book> response = restTemplate.postForEntity(baseUrl, postDto, Book.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
    }

    @Test
    @Order(2)
    void shouldGetBookSuccessfully() {
        Long bookId = 1L;
        ResponseEntity<BookDTO.Response> response = restTemplate.getForEntity(baseUrl + "/" + bookId, BookDTO.Response.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void shouldGetAllBooksSuccessfully() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(4)
    void shouldUpdateBookDetailSuccessfully() {
        Long bookId = 1L;
        BookDTO.Put putDto = new BookDTO.Put("Updated Title", "Updated SubTitle", "Updated Author", "Updated Publisher", Book.Status.AVAILABLE);

        restTemplate.put(baseUrl + "/" + bookId, putDto);

        ResponseEntity<Book> response = restTemplate.getForEntity(baseUrl + "/" + bookId, Book.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(putDto.getTitle() + " - " + putDto.getSubTitle(), response.getBody().getTitle());
    }

    @Test
    @Order(5)
    void shouldDeleteBookSuccessfully() {
        Long bookId = 1L;
        restTemplate.delete(baseUrl + "/" + bookId);

        try {
            ResponseEntity<Void> response = restTemplate.getForEntity(baseUrl + "/" + bookId, Void.class);
            fail("Excepted an exception to be thrown");
        } catch (HttpClientErrorException.NotFound e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }
}