package onezerodan.cocktailbot.model;

import javax.persistence.*;

@Entity(name = "ingredient")
//@Table(name = "ingredients")
public class Ingredient {

    @Id
    private Long id;
    private String name;
    private int amount;
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
    @JoinColumn(name = "cocktail_name")
    private Cocktail cocktail;


}
