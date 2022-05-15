package onezerodan.cocktailbot.repository;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CocktailRepository extends CrudRepository<Cocktail, Long> {

    boolean existsByName(String name);

    String findCocktailByIngredientsIn(Collection<String> ingredients);

    Cocktail findCocktailByName(String name);


}
