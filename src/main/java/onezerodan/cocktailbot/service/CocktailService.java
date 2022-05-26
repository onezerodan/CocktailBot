package onezerodan.cocktailbot.service;

import onezerodan.cocktailbot.model.Cocktail;

import java.util.List;

public interface CocktailService {


    boolean existsByName(String name);
    List<Cocktail> findByName(String name);
    List<Cocktail> suggestIfNotFound(String name);
    List<Cocktail> findByIngredientsAll(List<String> ingredientsName);

    void save(Cocktail cocktail);
}
