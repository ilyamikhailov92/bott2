package Com.service;

import Com.Model.EventDto;
import Com.Model.UserDto;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class postgreOps {

    public static String update(String event_datetime, String note) throws SQLException, ParseException {
        Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/bott2?currentSchema=public", "postgres", "qDZ23UG1234");
        Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = stmt.executeQuery("SELECT * FROM events");

        //Date dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").parse(event_datetime);
        //Long parsedDate = Long.parseLong(event_datetime);
        //Timestamp dateTimeStamp=new Timestamp(parsedDate);

        resultSet.moveToInsertRow();
        resultSet.updateInt(1,5);
        resultSet.updateString(2, "155965744");
        resultSet.updateTimestamp(3, Timestamp.valueOf("2012-05-11 20:53:59.000000"));  //event_datetime
        resultSet.updateString(4, event_datetime);
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
