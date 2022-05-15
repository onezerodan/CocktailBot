package onezerodan.cocktailbot.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity(name = "ingredient")

public class Ingredient {


    @Id
    @SequenceGenerator(
            name = "ingredient_sequence",
            sequenceName = "ingredient_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ingredient_sequence"
    )
    private Long id;



    @NotNull
    private String name;
    @NotNull
    private int amount;
    @NotNull
    private String unit;

    public Ingredient(String name, int amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public Ingredient() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "cocktail_name", referencedColumnName = "name"),
            @JoinColumn(name = "cocktail_id", referencedColumnName = "id")
    })
    private Cocktail cocktail;


}
