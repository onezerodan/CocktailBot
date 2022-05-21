package onezerodan.cocktailbot.service;

import onezerodan.cocktailbot.model.Cocktail;

import java.util.List;

public interface CocktailService {


    boolean existsByName(String name);
    Cocktail findByName(String name);
    List<String> findByIngredientsAll(List<String> ingredientsName);

    void save(Cocktail cocktail);
}