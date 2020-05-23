package Com.web;

import Com.service.dbOps;
import Com.service.Calculate;
import Com.service.postgreOps;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@RestController
@RequestMapping("api/v1")
public class Controller {

    @GetMapping("send")
    public String lol() {
        return "lol1111";
    }

    @GetMapping("fileInfo")
    public String getInfo() throws IOException {
        String getResult = dbOps.getOp();
        return getResult;
    }

    @PostMapping("calculate/firstNumber/{firstNumber}/secondNumber/{secondNumber}")
    public Integer calc(@PathVariable Integer firstNumber, @PathVariable Integer secondNumber) throws IOException {
        int result = Calculate.summ(firstNumber, secondNumber);
        return result;
    }

    @PostMapping("saveToDb/{content}")
    public String save(@PathVariable String event_datetime, @PathVariable String note, @PathVariable String chatId) throws IOException, SQLException, ParseException {
        return postgreOps.update(event_datetime, note, chatId);
    }

    /*
    @PostMapping("saveToDb/{content}")
    public String save(@PathVariable String content) throws IOException {
       return dbOps.writeOp(content);
    }
    */
}
