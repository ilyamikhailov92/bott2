package Com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.time.LocalDate;

public class BirthdayFinder {

    @Autowired
    TelegramBot message;

    public static String checkDate(String inputStream) throws TelegramApiException {

        String res = null;
        Boolean hasEventCheckResult;

        LocalDate date = LocalDate.now();
        String convertedDate = date.toString();
        String cutConvertedDate = convertedDate.substring(5, 10);

        /*
        //TBD: приведение даты к нужному формату
        String europeanDatePattern = "dd.MM.yyyy";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
        String formattedDate = europeanDateFormatter.format(date);
        */

        if (inputStream.contains(cutConvertedDate)) {
            //hasEventCheckResult = true;
            res = "Birthday!";
        } else {
            //hasEventCheckResult = false;
            res = "No birthday!";
        }
        return res;
    }
}
