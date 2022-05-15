package onezerodan.cocktailbot.job;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.model.CocktailTag;
import onezerodan.cocktailbot.model.Ingredient;
import onezerodan.cocktailbot.repository.CocktailRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Component
public class Parser {

    //CocktailService cocktailService;
    CocktailRepository cocktailRepository;

    public Parser(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }



    public void parse(String url) throws IOException {

        System.out.println(cocktailRepository);



        Document doc = Jsoup.connect(url)
                .userAgent("Opera")
                .referrer("http://www.google.com")
                .timeout(0)
                .get();

        Elements elements = doc.getElementsByClass("cocktail-item");

        int count = 0;
        for (Element cocktailElement : elements) {
            String cocktailName = cocktailElement.getElementsByClass("cocktail-item-name").text();
            if (cocktailRepository.existsByName(cocktailName)){
                continue;
            }
            Cocktail cocktail = new Cocktail(cocktailName);
            System.out.println("-----------------------------");
            System.out.println(cocktailElement.getElementsByClass("cocktail-item-name").text() +
                     " (" + "https://ru.inshaker.com" + cocktailElement.getElementsByClass("cocktail-item-preview").attr("href") + ")");

            Document cocktailDoc = null;

            try {
                cocktailDoc = Jsoup.connect("https://ru.inshaker.com" + cocktailElement.getElementsByClass("cocktail-item-preview").attr("href"))
                        .userAgent("Opera")
                        .referrer("https://www.google.com")
                        .timeout(0)
                        .get();
            }
            catch (SocketTimeoutException e) {
                System.out.println("ERROR");
            }


            assert cocktailDoc != null;
            Element ingredientsTable = cocktailDoc.getElementsByClass("ingredient-tables").select("table").first();

            System.out.println("Ингредиенты: ");
            assert ingredientsTable != null;
            Elements ingredients = ingredientsTable.getElementsByClass("name");
            for (Element ingredientsElement : ingredients) {
                String ingredientName = ingredientsElement.text();
                int ingredientAmount = Integer.parseInt(ingredientsElement.nextElementSibling().text());
                String ingredientUnit = ingredientsElement.nextElementSibling().nextElementSibling().text();

                Ingredient ingredient = new Ingredient(ingredientName, ingredientAmount, ingredientUnit);
                cocktail.addIngredient(ingredient);
                System.out.println(ingredientName + " " + ingredientAmount + " " + ingredientUnit);

            }

            System.out.println("Tags: +++++++++++++");
            Elements tagElements = cocktailDoc.getElementsByClass("tag");
            for (Element tagElement : tagElements) {
                String tagName = tagElement.text();
                System.out.println(tagName);
                cocktail.addTag(new CocktailTag(tagName));
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


            cocktailRepository.save(cocktail);
            //this.repository.save(cocktail);
            //System.out.println(repository);


        }
        System.out.println("Total cocktails: " + count);

    }


}
