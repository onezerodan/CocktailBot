package onezerodan.cocktailbot.service;

import onezerodan.cocktailbot.model.Cocktail;

import java.util.List;

public interface CocktailService {


    boolean existsByName(String name);
    List<Cocktail> findAllByName(String name);
    Cocktail findByName(String name);
    Cocktail findById(long id);
    List<Cocktail> suggestIfNotFound(String name);
    List<Cocktail> findByIngredientsAll(List<String> ingredientsName);
    List<String> getAllAvailableTags();
    List<Cocktail> findByTags(List<String> tagsName);

    void save(Cocktail cocktail);
}
