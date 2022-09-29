package cs590.awesomedelivery.searchsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SearchSvcApplication {
	public static void main(String[] args) {
		SpringApplication.run(SearchSvcApplication.class, args);
	}
}
