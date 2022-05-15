package onezerodan.cocktailbot.model;

import com.sun.istack.NotNull;
import onezerodan.cocktailbot.model.id.CocktailId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "cocktail")
@IdClass(CocktailId.class)
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


    @Id
    @NotNull
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumns({
            @JoinColumn(name = "cocktail_id", referencedColumnName = "id"),
            @JoinColumn(name = "cocktail_name",     referencedColumnName = "name")
    })
    @NotNull
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    /*
    @JoinColumn(
            name = "cocktail_name",
            referencedColumnName = "name"
    )
     */
    @JoinColumns({
            @JoinColumn(name = "cocktail_id", referencedColumnName = "id"),
            @JoinColumn(name = "cocktail_name",     referencedColumnName = "name")
    })
    @NotNull
    private List<CocktailTag> tags = new ArrayList<>();

    @Column(length=1024)
    private String recipe;

    public List<CocktailTag> getTags() {
        return tags;
    }

    public void setTags(List<CocktailTag> tags) {
        this.tags = tags;
    }

    public Cocktail() {

    }

    @Override
    public String toString() {
        return "Cocktail{" +
                ", name='" + name + '\'' +
                ", recipe='" + recipe + '\'' +
                '}';
    }

    public Long getId() { return id; }

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

    public void addTag(CocktailTag tag) {
        tags.add(tag);
    }


    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }


}
