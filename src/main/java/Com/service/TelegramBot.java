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

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;


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

        String[] parsedMessage = null;

        String note, trimmedNote = null;


        if (messageText.contains("Добавить"))
        {
            SendMessage responseMsg = new SendMessage(chatId, "Какого типа событие добавить: ДР | Напоминание? \nФормат ДР: ДР <дд.мм.гггг> <Чей ДР>\nФормат напоминания: Напоминание <Когда напомнить> <Описание>");
            execute(responseMsg);
        }
        Boolean check = null;
        if (messageText.contains("ДР"))
        {
            //Распарсить
            parsedMessage = messageText.split(" ");
            List <String> date = new ArrayList<>();
            List <String> notes = new ArrayList<>();
            for (String i : parsedMessage)
            {
                if(i.matches("[0-9]+\\.[0-9]+\\.[0-9]+"))
                {date.add(i);}
                //проверка, чтобы слово ДР не было добавлено в список слов описания события
                check=i.equals("ДР");
                if((i.matches("[а-яА-Я]*")) && check==false)
                {notes.add(i);}
            }
            //преобразование списка слов события в строку
            note = notes.toString();
            trimmedNote = note.substring(1, note.length()-1);

            //Отправить в postgtreOps
            if(date.get(0) != null) {
                String res = postgreOps.update(date.get(0), trimmedNote, chatId);
            }

            //Отправить в бот сообщение об успешной записи
            SendMessage responseMsg = new SendMessage(chatId, "Запись добавлена успешно");
            execute(responseMsg);
        }

        /*

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
        */

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
