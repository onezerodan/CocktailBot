package onezerodan.cocktailbot.parser;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.model.Ingredient;
import onezerodan.cocktailbot.service.CocktailService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Parser {


    @Autowired
    private CocktailService service;


    public void parse(String url) throws IOException {

        System.out.println(service);



        Document doc = Jsoup.connect(url)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .timeout(10 * 1000)
                .get();

        Elements elements = doc.getElementsByClass("cocktail-item");

        int count = 0;
        for (Element cocktailElement : elements) {
            Cocktail cocktail = new Cocktail(cocktailElement.getElementsByClass("cocktail-item-name").text());
            System.out.println("-----------------------------");
            System.out.println(cocktailElement.getElementsByClass("cocktail-item-name").text() +
                     " (" + "https://ru.inshaker.com" + cocktailElement.getElementsByClass("cocktail-item-preview").attr("href") + ")");

            Document cocktailDoc = Jsoup.connect("https://ru.inshaker.com" + cocktailElement.getElementsByClass("cocktail-item-preview").attr("href"))
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .timeout(10 * 1000)
                    .get();

            Element ingredientsTable = cocktailDoc.getElementsByClass("ingredient-tables").select("table").first();

            System.out.println("Ингредиенты: ");
            Elements ingredients = ingredientsTable.getElementsByClass("name");
            for (Element ingredientsElement : ingredients) {
                String ingredientName = ingredientsElement.text();
                int ingredientAmount = Integer.parseInt(ingredientsElement.nextElementSibling().text());
                String ingredientUnit = ingredientsElement.nextElementSibling().nextElementSibling().text();

                Ingredient ingredient = new Ingredient(ingredientName, ingredientAmount, ingredientUnit);
                cocktail.addIngredient(ingredient);
                System.out.println(ingredientName + " " + ingredientAmount + " " + ingredientUnit);

            }

            Element stepsTable = cocktailDoc.getElementsByClass("steps").first();
            Elements steps = stepsTable.getElementsByTag("li");
            int stepsCount = 1;
            String recipe = "";
            System.out.println("Рецепт:");
            for (Element stepsElement : steps) {
                recipe += stepsCount + " " + stepsElement.text() + "\n";
                stepsCount++;
            }
            count++;

            cocktail.setRecipe(recipe);


            //this.repository.save(cocktail);
            //System.out.println(repository);


        }
        System.out.println("Total cocktails: " + count);

    }


}
