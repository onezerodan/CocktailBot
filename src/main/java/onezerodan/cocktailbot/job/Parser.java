package onezerodan.cocktailbot.job;

import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.model.CocktailTag;
import onezerodan.cocktailbot.model.Ingredient;
import onezerodan.cocktailbot.repository.CocktailRepository;
import onezerodan.cocktailbot.service.CocktailService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import onezerodan.cocktailbot.util.PropertiesLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Component
public class Parser {

    Logger log = LoggerFactory.getLogger(Parser.class);
    CocktailService cocktailService;
    Properties properties =  new PropertiesLoader().getProperties();

    public Parser(CocktailService cocktailService) {this.cocktailService = cocktailService; }


    public List<Cocktail> parse() throws IOException, InterruptedException {

        List <Cocktail> result = new ArrayList<>();

        Document doc = Jsoup
                .connect(properties.getProperty("fullCocktailLink"))
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .referrer("http://www.google.com")
                .timeout(0)
                .get();

        Elements elements = doc.getElementsByClass("cocktail-item");
        int cocktailAmount = elements.size();
        int cocktailCount = 1;



        for (Element cocktailElement : elements) {
            Cocktail cocktail = new Cocktail();

            String cocktailName = getCocktailName(cocktailElement);


            if (cocktailService.existsByName(cocktailName)){
                cocktailCount++;
                log.warn(cocktailName + " Exists. Skipping.");
                continue;
            }
            cocktail.setName(cocktailName);




            Document cocktailDoc = null;

            try {
                cocktailDoc = Jsoup.connect(properties.getProperty("domain") + cocktailElement.getElementsByClass("cocktail-item-preview").attr("href"))
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("https://www.google.com")
                        .timeout(10 * 1000)
                        .get();
            } catch (SocketTimeoutException e) {
                log.error("Error parsing " + cocktailName + ".\n Retrying...");
                TimeUnit.SECONDS.sleep(10);
                while (cocktailDoc == null) {
                    cocktailDoc = Jsoup.connect(properties.getProperty("domain") + cocktailElement.getElementsByClass("cocktail-item-preview").attr("href"))
                            .userAgent("Opera")
                            .referrer("https://www.google.com")
                            .timeout(10 * 1000)
                            .get();
                    TimeUnit.SECONDS.sleep(1);
                }
                //continue;
            }


            Element ingredientsTable = cocktailDoc.getElementsByClass("ingredient-tables").select("table").first();

            cocktail.setIngredients(getCocktailIngredients(ingredientsTable));

            cocktail.setIngredient_names(getCocktailIngredientNames(ingredientsTable));


            Elements tagElements = cocktailDoc.getElementsByClass("tag");
            cocktail.setTags(getCocktailTags(tagElements));

            Element stepsTable = cocktailDoc.getElementsByClass("steps").first();
            Elements steps = stepsTable.getElementsByTag("li");

            cocktail.setRecipe(getCocktailRecipe(steps));


            result.add(cocktail);

            log.info(cocktailCount + "/" + cocktailAmount + " " +
                    "[Parsed] " + cocktail.getName());
            cocktailCount++;

        }
        return result;

    }

    private String getCocktailName(Element cocktailElement) {
        return cocktailElement.getElementsByClass("cocktail-item-name").text().toLowerCase();
    }

    private String getCocktailIngredientNames(Element ingredientsTable) {

        StringBuilder result = new StringBuilder();
        String SEPARATOR = "";

        Elements ingredients = ingredientsTable.getElementsByClass("name");

        for (Element ingredientsElement : ingredients) {
            String ingredientName = ingredientsElement.text().toLowerCase();
            int ingredientAmount = Integer.parseInt(ingredientsElement.nextElementSibling().text());
            String ingredientUnit = ingredientsElement.nextElementSibling().nextElementSibling().text().toLowerCase();

            result.append(SEPARATOR)
                    .append(ingredientName);
            SEPARATOR = ",";
        }
        return result.toString();
    }

    private List<Ingredient> getCocktailIngredients(Element ingredientsTable) {

        List<Ingredient> result = new ArrayList<>();
        Elements ingredients = ingredientsTable.getElementsByClass("name");

        for (Element ingredientsElement : ingredients) {
            String ingredientName = ingredientsElement.text().toLowerCase();
            int ingredientAmount = Integer.parseInt(ingredientsElement.nextElementSibling().text());
            String ingredientUnit = ingredientsElement.nextElementSibling().nextElementSibling().text().toLowerCase();

            Ingredient ingredient = new Ingredient(ingredientName, ingredientAmount, ingredientUnit);
            result.add(ingredient);
        }
        return result;
    }

    private List<CocktailTag> getCocktailTags(Elements tagElements) {

        List<CocktailTag> result = new ArrayList<>();

        for (Element tagElement : tagElements) {
            String tagName = tagElement.text().toLowerCase();
            result.add(new CocktailTag(tagName));
        }
        return result;
    }

    private String getCocktailRecipe(Elements steps) {

        int stepsCount = 1;
        StringBuilder recipe = new StringBuilder();
        for (Element stepsElement : steps) {
            recipe.append(stepsCount).append(". ").append(stepsElement.text()).append("\n");
            stepsCount++;
        }
        return recipe.toString();
    }


}
