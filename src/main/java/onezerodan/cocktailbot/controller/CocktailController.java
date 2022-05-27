package onezerodan.cocktailbot.controller;

import onezerodan.cocktailbot.bot.TgBot;
import onezerodan.cocktailbot.job.Parser;
import onezerodan.cocktailbot.model.Cocktail;
import onezerodan.cocktailbot.service.CocktailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.List;

@RestController
public class CocktailController {

    Logger log = LoggerFactory.getLogger(CocktailController.class);

    @Autowired
    CocktailService cocktailService;

    @GetMapping(value = "/updateCocktailDb")
    public ResponseEntity<String> saveCock() throws IOException, InterruptedException {
        Parser parser = new Parser(cocktailService);

        List<Cocktail> cocktails = parser.parse();

        for (Cocktail cocktail : cocktails) {
            cocktailService.save(cocktail);
        }
        log.info("Cocktails saved to database.");
        return new ResponseEntity<>("Added " + cocktails.size() + " cocktails.", HttpStatus.OK);

    }
}
