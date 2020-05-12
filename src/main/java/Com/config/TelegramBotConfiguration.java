package Com.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.BotSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
@ConditionalOnBean(value = {TelegramLongPollingBot.class})
public class TelegramBotConfiguration {

    static {
        ApiContextInitializer.init();
    }

    private List<BotSession> sessions = new ArrayList<>();
    @Autowired
    private List<TelegramLongPollingBot> pollingBots = new ArrayList<>();

    @PostConstruct
    public void start() {
        log.info("Starting auto config for telegram bots");
        TelegramBotsApi api = new TelegramBotsApi();
        pollingBots.forEach(bot -> {
            try {
                log.info("Registering polling bot: {}", bot.getBotUsername());
                sessions.add(api.registerBot(bot));
            } catch (TelegramApiException e) {
                log.error("Failed to register bot {} due to error {}", bot.getBotUsername(), e.getMessage());
            }
        });
    }

    @PreDestroy
    public void stop() {
        sessions.forEach(session -> {
            if (session != null) {
                session.stop();
            }
        });
    }


}
