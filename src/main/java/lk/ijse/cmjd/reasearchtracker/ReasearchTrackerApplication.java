package lk.ijse.cmjd.reasearchtracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class ReasearchTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReasearchTrackerApplication.class, args);
	}
        SpringApplication.run(ResearchTrackerApplication.class, args);


}
