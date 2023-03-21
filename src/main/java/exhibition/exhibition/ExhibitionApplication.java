package exhibition.exhibition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ExhibitionApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExhibitionApplication.class, args);
	}
}
