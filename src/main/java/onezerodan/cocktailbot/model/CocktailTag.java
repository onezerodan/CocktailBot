package onezerodan.cocktailbot.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;

//@SQLInsert(sql = "INSERT IGNORE INTO tag(name) " + "VALUES (?, ?)" )
@Entity(name = "tag")

public class CocktailTag {


    @Id
    @SequenceGenerator(
            name = "tag_sequence",
            sequenceName = "tag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tag_sequence"
    )
    private Long id;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "cocktail_name", referencedColumnName = "name"),
            @JoinColumn(name = "cocktail_id", referencedColumnName = "id")
    })
    private Cocktail cocktail;

    public CocktailTag(String tagName) {
        this.name = tagName;
    }

    public CocktailTag() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
