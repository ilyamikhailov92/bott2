package com.service;

import com.model.EventDto;
import com.model.UserDto;


import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class postgreOps {


    public static String update(String event_datetime, String note, String chatId) throws SQLException, ParseException {
        String outputText = null;
        Date date = null;


        //List<String> filteredData = new ArrayList<>();

        Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/bott2?currentSchema=public", "postgres", "qDZ23UG1234");
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = stmt.executeQuery("SELECT * FROM events");

        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        DateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        date = inputFormat.parse(event_datetime);
        outputText = outputFormat.format(date);

        /*
        //Проверка для нескольких форматов
        List<SimpleDateFormat> inputFormats = new ArrayList<SimpleDateFormat>();
        inputFormats.add(new SimpleDateFormat("dd.MM.yyyy"));
        inputFormats.add(new SimpleDateFormat("dd.MM"));
        inputFormats.add(new SimpleDateFormat("yyyy-MM-dd"));
        */

        //Получение количества строк
        int size = 0;
        int incrementedSize = 0;
        if (resultSet != null)
        {
            resultSet.last();
            size = resultSet.getRow();
        }

        //Получение
        incrementedSize=size+1;

        //
        resultSet.moveToInsertRow();
        resultSet.updateInt(1,incrementedSize);
        resultSet.updateString(2, chatId);
        resultSet.updateTimestamp(3, Timestamp.valueOf(outputText));//Timestamp.valueOf(event_datetime));  //Целевой формат: 2012-05-11 20:53:59.000000
        resultSet.updateString(4, "event_datetime");
        resultSet.updateString(5, note);
        resultSet.insertRow();

        return "OK";
    }

    public static List<UserDto> fetchUserList() throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/bott2?currentSchema=public", "postgres", "qDZ23UG1234");
        Statement stmt = connection.createStatement();

        ResultSet resultSet = stmt.executeQuery("SELECT * FROM users");
        List<UserDto> userList = new ArrayList<>();

        while (resultSet.next()) {

            String id = resultSet.getString("id");
            String chat_id = resultSet.getString("chat_id");
            String user_name = resultSet.getString("user_name");
            String note = resultSet.getString("note");

            UserDto user = UserDto.builder()
                    .id(id)
                    .chat_id(chat_id)
                    .user_name(user_name)
                    .note(note)
                    .build();

            userList.add(user);
        }
        return userList;

    }

    public static List<EventDto> fetchEventList() throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/bott2?currentSchema=public", "postgres", "qDZ23UG1234");
        Statement stmt = connection.createStatement();

        ResultSet eventInfo = stmt.executeQuery("select * from bott2.public.events");

        List<EventDto> eventList = new ArrayList<>();

        while (eventInfo.next()) {

            String id = eventInfo.getString("id");
            String chat_id = eventInfo.getString("chat_id");
            String schedule = eventInfo.getString("schedule");
            String event_datetime = eventInfo.getString("event_datetime");
            String note = eventInfo.getString("note");

            EventDto event = EventDto.builder()
                    .id(id)
                    .chat_id(chat_id)
                    .schedule(schedule)
                    .event_datetime(event_datetime)
                    .note(note)
                    .build();

            eventList.add(event);
        }
        return eventList;

    }


}
