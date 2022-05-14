package onezerodan.cocktailbot.service;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CocktailService {

    @Autowired
    private CocktailRepository repository;

    public void save(Cocktail cocktail) {
        repository.save(cocktail);
    }

}
