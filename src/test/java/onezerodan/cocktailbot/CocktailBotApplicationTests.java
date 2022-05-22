package onezerodan.cocktailbot;

import onezerodan.cocktailbot.model.Cocktail;
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
        System.out.println(cocktailRepository.findAllByName("Президент").toString());
    }



    @Test
    void findCocktailByName() {

        String name = "маргарита";
        List<Cocktail> cocktails = cocktailService.findByName(name);
        if (cocktails.size() == 1) {
            for (Cocktail cocktail : cocktails) {
                System.out.println(cocktail.toString());
            }
        }
        if (cocktails.size() > 1) {
            System.out.println("По вашему запросу найдено несколько коктейлей:");
            for (Cocktail cocktail : cocktails) {
                System.out.println(cocktail.getName());
            }
        }

        else {

            List<Cocktail> suggestions = cocktailService.suggestIfNotFound(name);
            if (suggestions.size() > 0) {
                System.out.println("\nВозможно, вы имели ввиду: ");
                for (Cocktail cocktail : suggestions) {
                    System.out.println(cocktail.getName());
                }
            }
            else {
                System.out.println("По вашему запросу ничего не найдено");
            }
        }


    }



    @Test
    void findCocktailByIngredientsAll() {
        List<String> ingredientNames = new ArrayList<>();
        ingredientNames.add("джин");
        ingredientNames.add("тоник");
        System.out.println(cocktailService.findByIngredientsAll(ingredientNames));
    }



}
