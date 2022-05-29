package onezerodan.cocktailbot.repository;

import onezerodan.cocktailbot.model.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    boolean existsByName(String name);

    @Query (value = "select distinct name from tag", nativeQuery = true)
    List<String> getAllAvailableTags();

    @Query (value = "select * from cocktail where tag_names ~* :regexp", nativeQuery = true)
    List<Cocktail> findByTags(@Param("regexp") String regexp);

    @Query (value = "select * from cocktail where ingredient_names ~*  :regexp", nativeQuery = true)
    List<Cocktail> findByIngredientsNameAllRegexp(@Param("regexp") String regexp);

    @Query (value = "select * from cocktail where levenshtein(name, :name) <= 2", nativeQuery = true)
    List<Cocktail> suggestByName(@Param("name") String name);

    @Query (value = "select * from cocktail where name ~* :regexp", nativeQuery = true)
    List<Cocktail> findAllByName(@Param("regexp") String regexp);

    @Query (value = "select * from cocktail  where name = :name", nativeQuery = true)
    Cocktail findByName(@Param("name") String name);

    String findCocktailByIngredientsIn(Collection<String> ingredients);




}
