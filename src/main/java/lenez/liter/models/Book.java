package lenez.liter.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authors;

    private String languages;
    private Long download_count;

    public Book() {
    }
    public Book(String title, List<AuthorData> authors, List<String> languages, Long download_count) {
        this.title = title;
        this.authors = authors.stream().map(a -> new Author(a.name(), a.birthYear(), a.deathYear())).
                collect(Collectors.toList());
        this.languages = String.valueOf(languages);
        this.download_count = download_count;
    }

    public String getTitle() {
        return this.title;
    }
    public String getAuthors() {
        return this.authors.toString();
    }
    public List<Author> getAuthorsList() {
        return this.authors;
    }
    public String getLanguages() {
        return this.languages;
    }
    public Long getDownloadCount() {
        return this.download_count;
    }

    @Override
    public String toString() {
        return "\n=====================================" +
                "\nTitle: " + title +
                "\nAuthors: " + authors +
                "\nLanguages: " + languages +
                "\nDownload count: " + download_count +
                "\n=====================================\n";
    }
}