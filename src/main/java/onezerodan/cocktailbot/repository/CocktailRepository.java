package onezerodan.cocktailbot.repository;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    boolean existsByName(String name);

    @Query (value = "select name from cocktail where ingredient_names ~*  :regexp", nativeQuery = true)
    List<String> findByIngredientsNameAllRegexp(@Param("regexp") String regexp);

    Cocktail findByName(String name);

    String findCocktailByIngredientsIn(Collection<String> ingredients);

    Cocktail findCocktailByName(String name);


}
