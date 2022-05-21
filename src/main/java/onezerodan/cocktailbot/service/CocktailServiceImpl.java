package onezerodan.cocktailbot.service;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailServiceImpl implements CocktailService{

    @Autowired
    private CocktailRepository repository;


    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Cocktail findByName(String name){
        return repository.findByName(name);
    };

    @Override
    public List<String> findByIngredientsAll(List<String> ingredientsNames) {
        StringBuilder regexp = new StringBuilder();
        for (String ingredientName : ingredientsNames) {
            regexp.append("(?=.*")
                    .append(ingredientName)
                    .append(")");
        }

        return repository.findByIngredientsNameAllRegexp(regexp.toString());
    }

    @Override
    public void save(Cocktail cocktail) {
        repository.save(cocktail);
    }


}
