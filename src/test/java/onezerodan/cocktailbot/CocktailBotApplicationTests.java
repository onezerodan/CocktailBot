package onezerodan.cocktailbot;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.model.Ingredient;
import onezerodan.cocktailbot.repository.CocktailRepository;
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

    @Test
    void checkCocktailRepositoryNotNull() {
        assert cocktailRepository != null;
    }

    @Test
    void checkCocktailRepositorySave() {
        Cocktail cocktail = new Cocktail();
        cocktailRepository.save(cocktail);
    }

    //@Autowired
    //CocktailService cocktailService;



    @Test
    void checkIfCocktailExistsByName() {
        System.out.println(cocktailRepository.existsByName("МаргаРИта"));
    }

    @Test
    void findByNameIgnoreCase() {
        System.out.println(cocktailRepository.findByName("Президент").toString());
    }

    @Test
    void findCocktailByIngredients() {
        List<String> ingredientNames = new ArrayList<>();
        ingredientNames.add("ликер корицы");
        ingredientNames.add("лимонный сок");
        System.out.println(
                cocktailRepository.findByIngredientsName(ingredientNames));
    }

    @Test
    void findCocktailByName() {
        System.out.println(
                cocktailRepository
                        .findCocktailByName("Голубая лагуна")
                        .toString()
        );
    }



}
