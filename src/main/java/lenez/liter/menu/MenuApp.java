package lenez.liter.menu;

import lenez.liter.models.Author;
import lenez.liter.models.Book;
import lenez.liter.models.BookData;
import lenez.liter.models.BooksData;
import lenez.liter.repositories.AuthorRepository;
import lenez.liter.repositories.BookRepository;
import lenez.liter.services.APIConnection;
import lenez.liter.services.DataSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuApp {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    Scanner sc = new Scanner(System.in);
    APIConnection apiConnection = new APIConnection();
    DataSerializer dataSerializer = new DataSerializer();

    public MenuApp(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void run() throws IOException {
        System.out.println("Welcome to Liter!");
        do {
            showMenu();
            try {
                int option = sc.nextInt(); sc.nextLine();
                switch (option) {
                    case 1:
                        handleRegisterBook();
                        break;
                    case 2:
                        handleSelectBooks();
                        break;
                    case 3:
                        handleListAuthors();
                        break;
                    case 4:
                        handleListAliveAuthors();
                        break;
                    case 5:
                        handleListBooksByLanguage();
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid option");
                sc.next();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
            System.out.println("Press any key to continue...");
            System.in.read();
        }  while (true);
    }
    private void handleRegisterBook() throws IOException {
        System.out.println("Please enter the name of the book:");
        String bookName = sc.nextLine();
        String response = apiConnection.getResponse(bookName);
        if(response == null || response.trim().isEmpty()) {
            System.out.println("Book not found");
            return;
        }
        Optional<Book> book = dataSerializer.deserialize(response, BooksData.class).books().stream().
                map(b -> new Book(b.title(), b.authors(), b.languages(), b.download_count())).findFirst();
        if(!book.isPresent()) {
            System.out.println("Book not found");
            return;
        }
        System.out.println("=====================================");
        System.out.println("Book found: " + book.get().getTitle());
        System.out.println("Authors: " + book.get().getAuthors());
        System.out.println("Languages: " + book.get().getLanguages());
        System.out.println("Download count: " + book.get().getDownloadCount());
        System.out.println("=====================================");
        if(bookRepository.findByTitleContaining(book.get().getTitle()).isEmpty()) {
            bookRepository.save(book.get());
        }
        book.get().getAuthorsList().forEach(a -> {
            if(authorRepository.findByNameContaining(a.getName()).isEmpty()) {
                authorRepository.save(a);
            }
        });
    }
    private void handleSelectBooks() throws IOException {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        System.out.println("Your reading list:");
        books.forEach(System.out::println);
    }
    private void handleListAuthors() throws IOException {
        List<String> authors = bookRepository.findAll().stream().map(Book::getAuthors).collect(Collectors.toList());
        if(authors.isEmpty()) {
            System.out.println("No authors found");
            return;
        }
        System.out.println("Authors:");
        authors.forEach(System.out::println);
    }
    private void handleListAliveAuthors() throws IOException {
        System.out.println("Please enter the year:");
        int year = sc.nextInt(); sc.nextLine();
        List<Author> authors = authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(year, year);
        if(authors.isEmpty()) {
            System.out.println("No authors found");
            return;
        }
        System.out.println("Authors:");
        authors.forEach(System.out::println);
    }
    private void handleListBooksByLanguage() throws IOException {
        System.out.println("Please enter the language:");
        String language = sc.nextLine();
        List<Book> books = bookRepository.findByLanguagesContaining(language);
        if(books.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        System.out.println("Books:");
        books.forEach(System.out::println);
    }
    private void showMenu() {
        System.out.println("Please select an option:");
        System.out.println("1. Register a book by name");
        System.out.println("2. View your reading list");
        System.out.println("3. View authors");
        System.out.println("4. View alive authors in a specific year");
        System.out.println("5. View books by language");
    }
}
