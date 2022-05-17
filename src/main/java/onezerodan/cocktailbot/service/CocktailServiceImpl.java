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
    public List<String> findByIngredientsAll(List<String> ingredientsName) {
        int match = ingredientsName.size();
        return repository.findByIngredientsNameAll(ingredientsName, match);
    }

    @Override
    public void save(Cocktail cocktail) {
        repository.save(cocktail);
    }


}
