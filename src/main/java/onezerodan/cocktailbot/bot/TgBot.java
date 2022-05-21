package onezerodan.cocktailbot.bot;

import onezerodan.cocktailbot.service.CocktailService;
import onezerodan.cocktailbot.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class TgBot extends TelegramLongPollingBot {

    Logger log = LoggerFactory.getLogger(TgBot.class);
    Properties properties = new PropertiesLoader().getProperties("bot");

    public TgBot(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @Autowired
    private CocktailService cocktailService;

    @Override
    public String getBotUsername() {
        return properties.getProperty("username");
    }

    @Override
    public String getBotToken() {
        return properties.getProperty("token");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText().toLowerCase();

        if (text.startsWith("/search")) {

            sendMessage(chatId, searchCocktailByName(text.replace("/search ", "")));
        }
        if (text.startsWith("/ingr")) {
            sendMessage(chatId, searchCocktailByIngredients(text.replace("/ingr ", "")));
        }
        sendMessage(chatId, text);
    }

    private String searchCocktailByName(String name) {
        return cocktailService.findByName(name).toString();
    }
    private String searchCocktailByIngredients(String ingredients) {
        List<String> ingredientsList = Arrays.asList(ingredients.split(", "));
        StringBuilder result = new StringBuilder();
        for (String name : cocktailService.findByIngredientsAll(ingredientsList)) {
            result.append(name).append("\n");
        }
        return result.toString();
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId.toString());
        msg.setText(text);

        try{
            execute(msg);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }


    }
}
