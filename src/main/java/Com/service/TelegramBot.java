package Com.service;

import Com.config.BotYmlConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import javax.annotation.PostConstruct;


@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private BotYmlConfig config;

    public TelegramBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update message) {
        //Получение инфо из сообщения
        String userName = message.getMessage().getFrom().getFirstName() + " " + message.getMessage().getFrom().getLastName();
        Long info = message.getMessage().getChatId();
        String chatId = info.toString();
        String messageText = message.getMessage().getText();

        //Проверка полученного сообшения на соответствие регулярному выражению
        boolean match = messageText.matches("[a-zA-Zа-яА-Я]*\\s[a-zA-Zа-яА-Я]*\\s[a-zA-Zа-яА-Я]*\\s[0-9]*[\\.|-][0-9]*[\\.|-][0-9]*");
        if (match == true)
        {
            dbOps.addOp("Success");
        }
        else
        {
            dbOps.addOp("Failure");
            String errorMsg = "Input correct date";
            SendMessage errMsg = new SendMessage(chatId,errorMsg);
            execute(errMsg);
        }
        //TBD: проверка на наличие дубликатов

        userName = userName.concat(' ' + chatId);
        String receivedInfo = userName.concat(' ' + messageText);
        dbOps.addOp(receivedInfo);

        SendMessage responseMsg = new SendMessage(chatId,receivedInfo);
        execute(responseMsg);

       // log.info("Пользователь {} написал {} ", userName,  message.getMessage().getText());

        //Long chatUserId = message.getMessage().getChatId();
        //String responseText = "какой-то ответ";

        //SendMessage responseMsg = new SendMessage(chatUserId, responseText);
    }

    public String getBotUsername() {
        return config.getTelegramTokenName();
    }

    public String getBotToken() {
        return config.getTelegramTokenValue();
    }

    @PostConstruct
    private void initBot() {
        log.warn("BOT: " + config.getTelegramTokenName() + "; TOKEN - " + config.getTelegramTokenValue());
    }
}
