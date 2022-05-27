package onezerodan.cocktailbot.model;

import com.sun.istack.NotNull;
import onezerodan.cocktailbot.model.id.CocktailId;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ingredient> ingredients = new ArrayList<>();

    private String ingredient_names;


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
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CocktailTag> tags = new ArrayList<>();

    private String tag_names;

    @Column(length=1024)
    private String recipe;


    public void setTag_names(String tag_names) {
        this.tag_names = tag_names;
    }

    public void setIngredient_names(String ingredient_names) {
        this.ingredient_names = ingredient_names;
    }




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
        StringBuilder ingredientsStr = new StringBuilder();
        for (Ingredient ingredient : getIngredients()) {
            ingredientsStr
                    .append("• ")
                    .append(ingredient.getName())
                    .append(" ")
                    .append(ingredient.getAmount())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }
        StringBuilder tagsStr = new StringBuilder();
        String tagSeparator = "";
        for (CocktailTag tag : tags) {
            tagsStr
                    .append(tagSeparator)
                    .append(tag.getName());
            tagSeparator = " / ";
        }
        StringBuilder answer = new StringBuilder();
        answer.append("🍹 " + name).append("\n\n")
                .append("🍊 Ингридиенты:").append("\n")
                .append(ingredientsStr).append("\n")
                .append("👨‍🍳 Способ приготовления:").append("\n")
                .append(recipe).append("\n")
                .append("🔖 Тэги: ").append("\n")
                .append(tagsStr);
        return answer.toString();
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
