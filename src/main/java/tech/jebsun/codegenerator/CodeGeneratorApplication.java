package tech.jebsun.codegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import tech.jebsun.codegenerator.configuration.SpringConfiguration;

@SpringBootApplication
@Import(SpringConfiguration.class)
public class CodeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeGeneratorApplication.class, args);
	}

}
