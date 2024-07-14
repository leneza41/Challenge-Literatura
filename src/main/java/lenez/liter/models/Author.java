package lenez.liter.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Nullable
    private Integer birthYear;

    @Nullable
    private Integer deathYear;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author() {
    }
    public Author(String name, int birthYear, int deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }
    public String getName() {
        return this.name;
    }
    @Override
    public String toString() {
        return "\n\tAuthor name: " + name + "; birth year: " + birthYear + "; death year: " + deathYear + "\n";
    }
}
