package onezerodan.cocktailbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication()
public class CocktailBotApplication  {

    static Logger log = LoggerFactory.getLogger(CocktailBotApplication.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CocktailBotApplication.class, args);
    }
}
