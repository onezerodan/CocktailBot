package onezerodan.cocktailbot;

import onezerodan.cocktailbot.job.Parser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;


//@EntityScan(basePackages = {"onezerodan.cocktailbot.model"})
//@EnableJpaRepositories(basePackages="onezerodan.cocktailbot.repository")
//@ComponentScan(basePackages = "onezerodan.cocktailbot.service")
@SpringBootApplication()
public class CocktailBotApplication  {



    public static void main(String[] args) throws IOException {
        SpringApplication.run(CocktailBotApplication.class, args);

    }



}
