package com.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.model.EventDto;
import com.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@EnableScheduling
@Service
public class Scheduler {

    @Autowired TelegramBot message;
    //@Scheduled (fixedRate = 50000)
    public void checkDate() throws TelegramApiException, SQLException, ClassNotFoundException
    {

        StringBuffer messageWithBD = new StringBuffer("");
        String checkDateResult = null;
        //--------
        //Получить инфо из БД
        //Инфо о пользователях
        List<UserDto> dbAllInfo = postgreOps.fetchUserList();
        UserDto firstUser = dbAllInfo.stream()
                .findFirst()
                .get();

        //Инфо о событиях
        List<EventDto> eventInfo = postgreOps.fetchEventList();
        List<String> filteredData = new ArrayList<>();
        //Проверить совпадение дат у событий из БД и текущей даты
        for(EventDto i: eventInfo)
        {
            checkDateResult = BirthdayFinder.checkDate(i.getEvent_datetime());
            if (checkDateResult != "No birthday!")
            {//добавить в новый список инфо о тех, у кого ДР
                filteredData.add(i.getNote());
            }
        }
        if(filteredData.isEmpty())
        {
            messageWithBD.append("Сегодня нет ДР");
        }
        else
        {
            messageWithBD.append("Сегодня ДР у:");
            for(String y: filteredData)
            {
                messageWithBD.append('\n' + y);
            }
        }


        SendMessage dateMsg = new SendMessage("155965744", String.valueOf(messageWithBD));
        message.execute(dateMsg);
    }
}


       /*
        EventDto someEvent = eventInfo.stream()
                .findFirst()
                .get();
        //--------
       */

        //String result = BirthdayFinder.checkDate(firstUser.getChat_id());

        //Вызвать проверку даты и получить результат
         //BirthdayFinder.checkDate(someEvent.getEvent_datetime());
        //Отправить результат в бот
        //SendMessage dateMsg = new SendMessage("155965744", String.valueOf(result));

        //SendMessage dateMsg = new SendMessage("155965744", someEvent.getNote().concat(' ' + checkDateResult));
        //message.execute(dateMsg);
        //TBD: Сделать разделение напоминаний:напоминать ежегодно, ежедневно, разово

