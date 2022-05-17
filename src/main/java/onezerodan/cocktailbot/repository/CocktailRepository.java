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

    @Query( value = "select cocktail_name from ingredient where name in :ingredients", nativeQuery = true)
    List<String> findByIngredientsName (@Param("ingredients") List<String> ingredients);

    @Query (value = "select cocktail_name from ingredient " +
            "where name in :ingredients " +
            "group by cocktail_name having count(*) = :match", nativeQuery = true)
    List<String> findByIngredientsNameAll (@Param("ingredients") List<String> ingredients, @Param("match") int match);

    List<Cocktail> findAllByIngredientsIn(List<Ingredient> ingredients);

    Cocktail findByName(String name);

    String findCocktailByIngredientsIn(Collection<String> ingredients);

    Cocktail findCocktailByName(String name);


}
