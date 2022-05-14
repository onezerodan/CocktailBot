package onezerodan.cocktailbot;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.repository.CocktailRepository;
import onezerodan.cocktailbot.service.CocktailService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CocktailBotApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private CocktailRepository cocktailRepository;

    @Test
    void checkCocktailRepositoryNotNull() {
        assert cocktailRepository != null;
    }

    @Test
    void checkCocktailRepositorySave() {
        Cocktail cocktail = new Cocktail("cocktail");
        cocktailRepository.save(cocktail);
    }

    @Autowired
    private CocktailService cocktailService;

    @Test
    void checkCocktailServiceNotNull() {
        assert cocktailService != null;
    }



}
