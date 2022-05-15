package onezerodan.cocktailbot;

import onezerodan.cocktailbot.model.Cocktail;
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
        Cocktail cocktail = new Cocktail("cocktail");
        cocktailRepository.save(cocktail);
    }

    //@Autowired
    //CocktailService cocktailService;



    @Test
    void checkIfCocktailExistsByName() {
        System.out.println(cocktailRepository.existsByName("ылвоадыловдаыовдлмты"));
    }

    @Test
    void findCocktailByIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("тоник");
        System.out.println(
                cocktailRepository.findCocktailByIngredientsIn(ingredients));
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
