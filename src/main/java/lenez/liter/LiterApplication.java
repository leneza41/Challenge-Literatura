package lenez.liter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lenez.liter.menu.MenuApp;

@SpringBootApplication
public class LiterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MenuApp menu = new MenuApp();
		menu.run();
	}
}
