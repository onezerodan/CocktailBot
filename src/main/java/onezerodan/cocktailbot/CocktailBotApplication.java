package onezerodan.cocktailbot;

import onezerodan.cocktailbot.bot.TgBot;
import onezerodan.cocktailbot.service.CocktailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

@SpringBootApplication()
public class CocktailBotApplication implements CommandLineRunner {

    @Autowired
    CocktailService cocktailService;

    static Logger log = LoggerFactory.getLogger(CocktailBotApplication.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CocktailBotApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(new TgBot(cocktailService));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
