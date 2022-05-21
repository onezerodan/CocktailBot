package onezerodan.cocktailbot;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.model.Ingredient;
import onezerodan.cocktailbot.repository.CocktailRepository;
import onezerodan.cocktailbot.service.CocktailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CocktailBotApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private CocktailRepository cocktailRepository;

    @Autowired
    CocktailService cocktailService;

    @Test
    void checkCocktailRepositoryNotNull() {
        assert cocktailRepository != null;
    }

    @Test
    void checkCocktailRepositorySave() {
        Cocktail cocktail = new Cocktail();
        cocktailRepository.save(cocktail);
    }





    @Test
    void checkIfCocktailExistsByName() {
        System.out.println(cocktailRepository.existsByName("МаргаРИта"));
    }

    @Test
    void findByNameIgnoreCase() {
        System.out.println(cocktailRepository.findByName("Президент").toString());
    }



    @Test
    void findCocktailByName() {
        System.out.println(
                cocktailService
                        .findByName("Маргарита")
                        .toString()
        );
    }

    @Test
    void findCocktailByIngredientsAll() {
        List<String> ingredientNames = new ArrayList<>();
        ingredientNames.add("джин");
        ingredientNames.add("тоник");
        System.out.println(cocktailService.findByIngredientsAll(ingredientNames));
    }



}
