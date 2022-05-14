package onezerodan.cocktailbot.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "cocktail")
//@Table(name = "cocktails")
public class Cocktail {

    @Id
    @SequenceGenerator(
            name = "cocktail_sequence",
            sequenceName = "cocktail_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cocktail_sequence"
    )
    private Long id;
    private String name;

    @OneToMany(
            mappedBy = "cocktail",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Ingredient> ingredients = new ArrayList<>();
    private String recipe;

    public Cocktail(String name) {
        this.name = name;

    }

    public Cocktail() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }


    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }


}
