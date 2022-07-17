package toyproject.pyeonsool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PyeonsoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(PyeonsoolApplication.class, args);
    }

}
