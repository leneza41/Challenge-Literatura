package lenez.liter;

import lenez.liter.repositories.AuthorRepository;
import lenez.liter.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lenez.liter.menu.MenuApp;

@SpringBootApplication
public class LiterApplication implements CommandLineRunner {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MenuApp menu = new MenuApp(bookRepository, authorRepository);
		menu.run();
	}
}
