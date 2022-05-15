package onezerodan.cocktailbot.controller;

import onezerodan.cocktailbot.job.Parser;
import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.repository.CocktailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CocktailController {

    Logger log = LoggerFactory.getLogger(CocktailController.class);

    @Autowired
    CocktailRepository cocktailRepository;

    @GetMapping(value = "/updateCocktailDb")
    public void saveCock() throws IOException, InterruptedException {
        Parser parser = new Parser(cocktailRepository);

        for (Cocktail cocktail : parser.parse()) {
            cocktailRepository.save(cocktail);
        }
        //cocktailRepository.saveAll(parser.parse());
        log.info("Cocktails saved to database.");

    }
}
