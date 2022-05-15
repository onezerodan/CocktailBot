package onezerodan.cocktailbot.controller;

import onezerodan.cocktailbot.job.Parser;
import onezerodan.cocktailbot.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CocktailController {

    //@Autowired
    //CocktailService cocktailService;

    @Autowired
    CocktailRepository cocktailRepository;

    @GetMapping(value = "/cock")
    public void saveCock() throws IOException {
        Parser parser = new Parser(cocktailRepository);
        parser.parse("https://ru.inshaker.com/cocktails?random_page=59");;
    }
}
